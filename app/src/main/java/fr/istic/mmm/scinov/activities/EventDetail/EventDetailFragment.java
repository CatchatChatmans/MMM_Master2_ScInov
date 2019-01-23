package fr.istic.mmm.scinov.activities.EventDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;

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

        return inflater.inflate(R.layout.activity_event_detail, container, false);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
////            DetailNestedFragment detailFragment = new DetailNestedFragment();
////            detailFragment.setArguments(getIntent().getExtras());
////
////            MapNestedFragment mapFragment = new MapNestedFragment();
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
