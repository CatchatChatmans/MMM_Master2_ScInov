package fr.istic.mmm.scinov.activities.EventDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Home.MainActivity;
import fr.istic.mmm.scinov.activities.Journey.fragment.JourneyDialogFragment;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class EventDetailFragment extends Fragment {

    private Event event;
    private EventViewModel viewModel;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView eventName = view.findViewById(R.id.detailsName);
        TextView eventTheme = view.findViewById(R.id.detailsTheme);
        ImageView eventImage = view.findViewById(R.id.app_bar_image);
        TextView eventDescription = view.findViewById(R.id.details_description);
        ImageView addToJourney = view.findViewById((R.id.addJourney));
        RatingBar ratingView = view.findViewById((R.id.details_rating));


        eventName.setText(event.getName());
        eventTheme.setText(event.getTheme());
        eventDescription.setText(event.getDescription());
        Picasso.get().load(event.getImageUrl()).into(eventImage);

        // TODO: Replace by currentUser userID
        ratingView.setRating((float) (double) event.getRatings().getOrDefault("Em2QrJxjaMYip8MgKnmcAET3dFk1", 0.0));


        ratingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // TODO: Replace by currentUser userID
                event.getRatings().put("Em2QrJxjaMYip8MgKnmcAET3dFk1",(double) rating);
                viewModel.setValue(event);
            }
        });


        ((MainActivity) getActivity()).getSupportActionBar().hide();
//        ((MainActivity) getActivity()).getSupportActionBar().invalidateOptionsMenu();
        Toolbar toolbar = view.findViewById(R.id.event_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(event.getName());

//        getActivity().findViewById(R.id.activity_drawer).setFitsSystemWindows(false);
//
//        Log.i("EVENT_DETAIL", Boolean.toString(getActivity().findViewById(R.id.activity_drawer).getFitsSystemWindows()));

        //fix for the title display issue
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.post(() -> collapsingToolbarLayout.requestLayout());

        addToJourney.setOnClickListener(v -> {
            JourneyDialogFragment jdf = JourneyDialogFragment.newInstance(event);
            jdf.show(getFragmentManager(), null);
        });

    }
}
