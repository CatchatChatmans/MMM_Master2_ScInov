package fr.istic.mmm.scinov.activities.Event.fragment;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Event.model.Event;
import fr.istic.mmm.scinov.activities.Event.model.EventViewModel;
import fr.istic.mmm.scinov.activities.Home.MainActivity;
import fr.istic.mmm.scinov.activities.Journey.fragment.JourneyDialogFragment;
import fr.istic.mmm.scinov.activities.Login.LoginFragment;
import fr.istic.mmm.scinov.model.FirebaseQueryLiveData;

public class EventDetailFragment extends Fragment {

    private Event event;
    private EventViewModel viewModel;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference EVENT_REF = FirebaseDatabase.getInstance().getReference("/users");
    private TextView eventWebsite;
    private View mainView;

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

        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainView = view;

        TextView eventName = view.findViewById(R.id.detailsName);
        TextView eventTheme = view.findViewById(R.id.detailsTheme);
        TextView eventAddr = view.findViewById(R.id.addr);
        TextView eventDates = view.findViewById(R.id.dates);
        ImageView eventImage = view.findViewById(R.id.app_bar_image);
        TextView eventDescription = view.findViewById(R.id.details_description);
        ImageView addToJourney = view.findViewById((R.id.addJourney));
        RatingBar ratingView = view.findViewById((R.id.details_rating));
        TextView eventSeatsLeft = view.findViewById(R.id.details_seats_left);

        ImageView updateSeats = view.findViewById(R.id.update_seats);
        TextView eventEmail = view.findViewById(R.id.details_email);
        TextView eventPhone = view.findViewById(R.id.details_phone);
        eventWebsite = view.findViewById(R.id.details_website);

        eventName.setText(event.getName());
        eventTheme.setText(event.getTheme().replaceAll("\\|", ", "));
        eventAddr.setText(event.getAddress());
        eventDates.setText(event.getHours());
        eventDescription.setText(event.getDescription());
        if(event.getLienInscription() != null){
            Matcher matcherEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(event.getLienInscription());
            while (matcherEmail.find()) {
                eventEmail.setText(matcherEmail.group());
            }
            Matcher matcherPhone = Pattern.compile("(0|\\+33|0033)[1-9]((.|\\s|-)?\\d{2}){4}").matcher(event.getLienInscription());
            while (matcherPhone.find()) {
                eventPhone.setText(matcherPhone.group());
            }
//            Matcher matcherWebsite = Pattern.compile("(?:^|[^@\\.\\w-])((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*").matcher(event.getLienInscription());
//            while (matcherWebsite.find()) {
//                eventWebsite.setText(matcherWebsite.group());
//            }
        }

        if(!event.getLink().isEmpty()){
            eventWebsite.setText(event.getLink());
        }

        if(event.getSeatsAvailable() != 0){
            eventSeatsLeft.setText((event.getSeatsAvailable() - event.getSeatsTaken()) + " places restantes");
        }

        eventWebsite.setOnClickListener(this::manageClick);
        eventAddr.setOnClickListener(this::manageClick);
        eventPhone.setOnClickListener(this::manageClick);
        eventDates.setOnClickListener(this::manageClick);
        eventEmail.setOnClickListener(this::manageClick);
        eventSeatsLeft.setOnClickListener(this::manageClick);

        hideInfoIfNull(eventDates, view.findViewById(R.id.ic_info_dates));
        hideInfoIfNull(eventPhone, view.findViewById(R.id.ic_info_phone));
        hideInfoIfNull(eventEmail, view.findViewById(R.id.ic_info_email));
        hideInfoIfNull(eventWebsite, view.findViewById(R.id.ic_info_website));
        hideInfoIfNull(eventAddr, view.findViewById(R.id.ic_info_place));
        hideInfoIfNull(eventSeatsLeft, view.findViewById(R.id.ic_info_seats));

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

        updateSeats.setVisibility(View.GONE);

        if(auth.getCurrentUser() != null){
            Log.i("USER ROLE", auth.getCurrentUser().getUid());
            FirebaseQueryLiveData userLiveData = new FirebaseQueryLiveData(EVENT_REF.child(auth.getCurrentUser().getUid()));
            userLiveData.observe(this, new Observer<DataSnapshot>() {
                @Override
                public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                    String role = (String) dataSnapshot.getValue();
                    if (role == null) return;
                    Log.i("USER ROLE", role);
                    if (role.equals("admin")){
                        updateSeats.setVisibility(View.VISIBLE);
                        updateSeats.setOnClickListener(v -> {
                            UpdateSeatsDialogFragment usdf = UpdateSeatsDialogFragment.newInstance(event);
                            usdf.show(getFragmentManager(), null);
                        });
                    }

                }
            });
        }

        ((MainActivity) getActivity()).getSupportActionBar().hide();
        Toolbar toolbar = view.findViewById(R.id.event_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(event.getName());;

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

    public void manageClick(View view){
        switch (view.getId()){
            case R.id.details_website:
                Log.i("DO STUFF","go to website");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getLink()));
                startActivity(intent);
                break;
            case R.id.details_phone:
                Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ((TextView) view).getText().toString()));
                startActivity(intentCall);
                break;
            default:
                    break;
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

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        Toolbar toolbar = mainView.findViewById(R.id.event_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        toolbar.setTitle(event.getName());
    }

    private boolean hideInfoIfNull(TextView textView, ImageView imageView){
        if(textView.getText().toString().isEmpty()){
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }
}
