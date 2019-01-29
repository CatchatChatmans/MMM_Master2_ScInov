package fr.istic.mmm.scinov.activities.Home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Event.fragment.EventsFragment;
import fr.istic.mmm.scinov.activities.Event.model.Event;
import fr.istic.mmm.scinov.activities.Event.model.EventViewModel;
import fr.istic.mmm.scinov.activities.Journey.fragment.JourneyListFragment;
import fr.istic.mmm.scinov.activities.Login.LoginFragment;
import fr.istic.mmm.scinov.activities.Map.FullMapFragment;
import fr.istic.mmm.scinov.model.FirebaseQueryLiveData;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference EVENT_REF = FirebaseDatabase.getInstance().getReference("/users");

    private EventViewModel viewModel;
    LiveData<List<Event>> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if(auth.getCurrentUser() != null){
            Log.i("USER ROLE", auth.getCurrentUser().getUid());
            FirebaseQueryLiveData userLiveData = new FirebaseQueryLiveData(EVENT_REF.child(auth.getCurrentUser().getUid()));
            userLiveData.observe(this, new Observer<DataSnapshot>() {
                @Override
                public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        Log.i("USER ROLE", (String) dataSnapshot.getValue());
                    }
                }
            });
        }

        // Set the toolbar as the action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.nav_list);

        // Set the drawer
        drawer = findViewById(R.id.activity_drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        drawer.setFitsSystemWindows(false);
//        drawer.post(() -> drawer.requestLayout());
        Log.i("MAIN_ACTIVITY", Boolean.toString(drawer.getFitsSystemWindows()));

        // Set the navigation
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_content,
                    new EventsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_events);
        }

        // Set the sign in button
        View header = navigationView.getHeaderView(0);
        TextView signInBtn = header.findViewById(R.id.signin);
        TextView emailAddress = header.findViewById(R.id.nav_email);

        if(auth.getCurrentUser() != null){
            signInBtn.setText(getString(R.string.btn_logout));
            emailAddress.setText(auth.getCurrentUser().getEmail());
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }else{
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTitle(R.string.nav_login);
                    drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new LoginFragment()).addToBackStack(null).commit();
                }
            });
        }

        // Get the same viewModel for all the fragments
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {

            case R.id.nav_events:
                getSupportActionBar().setTitle(R.string.nav_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new EventsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_journey:
                getSupportActionBar().setTitle(R.string.nav_journey);
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new JourneyListFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_map:
                getSupportActionBar().setTitle(R.string.nav_map);
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new FullMapFragment()).addToBackStack(null).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
}
