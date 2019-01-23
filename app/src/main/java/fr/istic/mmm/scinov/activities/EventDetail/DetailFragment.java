package fr.istic.mmm.scinov.activities.EventDetail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment{

    private Event event;
    private TextView eventName;
    private TextView eventTheme;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventName = view.findViewById(R.id.detailsName);
        eventTheme = view.findViewById(R.id.detailsTheme);
        Bundle bundle = getArguments();
        if(bundle != null){
            event = bundle.getParcelable("Event");
            eventName.setText(event.getName());
            eventTheme.setText(event.getTheme());
        }
    }
}
