package fr.istic.mmm.scinov.activities.EventDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;

public class EventDetailActivity extends Fragment {

    public EventDetailActivity() {
    }

    public static EventDetailActivity newInstance(Event event) {
        EventDetailActivity eventDetailActivity = new EventDetailActivity();
        Bundle args = new Bundle();
        args.putParcelable("Event", event);
        eventDetailActivity.setArguments(args);
        return eventDetailActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = new Bundle();
        args.putParcelable("Event", getArguments().getParcelable("Event"));

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(args);

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.detailFrame, detailFragment)
        .replace(R.id.mapFrame, mapFragment)
        .addToBackStack(null)
        .commit();

        return inflater.inflate(R.layout.activity_event_detail, container, false);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
////            DetailFragment detailFragment = new DetailFragment();
////            detailFragment.setArguments(getIntent().getExtras());
////
////            MapFragment mapFragment = new MapFragment();
////            mapFragment.setArguments(getIntent().getExtras());
////
////            FragmentManager fragmentManager = getSupportFragmentManager();
////            fragmentManager.beginTransaction()
////            .add(R.id.detailFrame, detailFragment)
////            .add(R.id.mapFrame, mapFragment)
////            .commit();
//
//    }

}
