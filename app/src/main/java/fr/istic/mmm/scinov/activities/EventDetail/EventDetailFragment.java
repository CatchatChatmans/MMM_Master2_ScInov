package fr.istic.mmm.scinov.activities.EventDetail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Home.MainActivity;
import fr.istic.mmm.scinov.activities.Journey.fragment.JourneyDialogFragment;
import fr.istic.mmm.scinov.activities.Login.LoginFragment;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class EventDetailFragment extends Fragment {

    private Event event;
    private EventViewModel viewModel;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public EventDetailFragment() {
    }

    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("Event", event);
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        event = getArguments().getParcelable("Event");

        Bundle args = new Bundle();
        args.putParcelable("Event", event);

        MapNestedFragment mapNestedFragment = new MapNestedFragment();
        mapNestedFragment.setArguments(args);

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.eventMapFrame, mapNestedFragment)
        .addToBackStack(null)
        .commit();

        // Observe the live data from the view model of the main activity to avoid reloading the data when going back
        viewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        LiveData<List<Event>> liveData = viewModel.getEventsLiveData();
        liveData.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {

            }
        });

        return inflater.inflate(R.layout.activity_event_detail, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView eventName = view.findViewById(R.id.detailsName);
        TextView eventTheme = view.findViewById(R.id.detailsTheme);
        TextView eventAddr = view.findViewById(R.id.addr);
        TextView eventDates = view.findViewById(R.id.dates);
        ImageView eventImage = view.findViewById(R.id.app_bar_image);
        TextView eventDescription = view.findViewById(R.id.details_description);
        ImageView addToJourney = view.findViewById((R.id.addJourney));
        RatingBar ratingView = view.findViewById((R.id.details_rating));
        TextView eventCoord = view.findViewById(R.id.coordInscr);
        Button eventSearchButton = view.findViewById(R.id.link);


        eventName.setText(event.getName());
        eventTheme.setText(event.getTheme());
        eventAddr.setText(event.getCity()+"("+event.getZipcode()+")\n"+event.getAddress());
        eventDates.setText(event.getHours());
        eventDescription.setText(event.getDescription());
        if(event.getLienInscription() != null)
            eventCoord.setText("Coordonées d'inscriptions: "+event.getLienInscription());
        eventSearchButton.setOnClickListener(this::searchButton);


        Picasso.get().load(event.getImageUrl()).into(eventImage);

        if(auth.getCurrentUser() != null){
            ratingView.setRating((float) (double) event.getRatings().getOrDefault(auth.getCurrentUser().getUid(), 0.0));
            ratingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    event.getRatings().put(auth.getCurrentUser().getUid(),(double) rating);
                    viewModel.setValue(event);
                }
            });
            addToJourney.setOnClickListener(v -> {
                JourneyDialogFragment jdf = JourneyDialogFragment.newInstance(event);
                jdf.show(getFragmentManager(), null);
            });
        }else{
            ratingView.setRating(0.0f);
            ratingView.setIsIndicator(true);
            ratingView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("SNACKBAR getActionMasked",Integer.toString(event.getActionMasked()));
                    Log.i("SNACKBAR getAction",Integer.toString(event.getAction()));
                    if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
                        showSnackbarLogin(view);
                        return true;
                    }
                    return false;
                }
            });
            addToJourney.setOnClickListener(v -> {
                showSnackbarLogin(view);
            });
        }

        ((MainActivity) getActivity()).getSupportActionBar().hide();
        Toolbar toolbar = view.findViewById(R.id.event_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(event.getName());

        //fix for the title display issue
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.post(() -> collapsingToolbarLayout.requestLayout());
    }

    private void showSnackbarLogin(View view){
        Snackbar mySnackbar = Snackbar.make(view, R.string.login_required, Snackbar.LENGTH_LONG);
        mySnackbar.setAction(R.string.nav_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new LoginFragment()).addToBackStack(null).commit();
            }
        });
        mySnackbar.show();
    }

    public void searchButton(View view){
        Log.i("SearchButtonClicked","addr:"+event.getLink());

        if(event.getLink() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getLink()));
            startActivity(intent);
        }else{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fetedelascience.fr/"));
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("EVENT DETAILS", "onStop");
        DrawerLayout drawer = getActivity().findViewById(R.id.activity_drawer);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
    }
}
