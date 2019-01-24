package fr.istic.mmm.scinov.activities.Journey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JourneyDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JourneyDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JourneyDetailFragment extends Fragment {
    //

    public JourneyDetailFragment() {
        // Required empty public constructor
    }


    public static JourneyDetailFragment newInstance(Journey journey) {

        JourneyDetailFragment eventDetailFragment = new JourneyDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("Journey", journey);
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journey_detail, container, false);
    }


}
