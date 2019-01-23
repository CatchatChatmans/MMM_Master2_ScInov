package fr.istic.mmm.scinov.activities.EventDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class EventDetailFragment extends Fragment {

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

        Bundle args = new Bundle();
        args.putParcelable("Event", getArguments().getParcelable("Event"));

        DetailNestedFragment detailNestedFragment = new DetailNestedFragment();
        detailNestedFragment.setArguments(args);

        MapNestedFragment mapNestedFragment = new MapNestedFragment();
        mapNestedFragment.setArguments(args);

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.detailFrame, detailNestedFragment)
        .replace(R.id.mainMapFrame, mapNestedFragment)
        .addToBackStack(null)
        .commit();

        // Observe the live data from the view model of the main activity to avoid reloading the data when going back
        EventViewModel viewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        LiveData<List<Event>> liveData = viewModel.getEventsLiveData();
        liveData.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {

            }
        });

        return inflater.inflate(R.layout.activity_event_detail, container, false);
    }
}
