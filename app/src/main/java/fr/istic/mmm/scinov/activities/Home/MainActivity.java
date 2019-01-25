package fr.istic.mmm.scinov.activities.Home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.list.JourneyListFragment;
import fr.istic.mmm.scinov.activities.Login.LoginFragment;
import fr.istic.mmm.scinov.activities.Map.FullMapFragment;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    private EventViewModel viewModel;
    LiveData<List<Event>> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set the toolbar as the action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.nav_list);

        // Set the drawer
        drawer = findViewById(R.id.activity_drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

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

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new LoginFragment()).addToBackStack(null).commit();
                setTitle(R.string.nav_login);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        // Get the same viewModel for all the fragments
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {

            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new EventsFragment()).addToBackStack(null).commit();
                setTitle(R.string.nav_list);
                break;
            case R.id.nav_journey:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new JourneyListFragment()).addToBackStack(null).commit();
                setTitle(R.string.nav_journey);
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new FullMapFragment()).addToBackStack(null).commit();
                setTitle(R.string.nav_map);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        final Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("EVENT_FRAGMENT");

        Log.i("BACK_PRESSED","onBackPressed");
        if (currentFragment != null && currentFragment.isVisible()) {
            Log.i("BACK_PRESSED", currentFragment.getTag());
            setSupportActionBar(toolbar);
            getSupportActionBar().show();
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
        }

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
