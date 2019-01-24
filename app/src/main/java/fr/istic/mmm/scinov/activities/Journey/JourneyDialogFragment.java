package fr.istic.mmm.scinov.activities.Journey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JourneyDialogFragment} interface
 * to handle interaction events.
 * Use the {@link JourneyDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JourneyDialogFragment extends DialogFragment {


    public JourneyDialogFragment() {
        // Required empty public constructor
    }

     public static JourneyDialogFragment newInstance() {
        JourneyDialogFragment fragment = new JourneyDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journey_dialog, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
