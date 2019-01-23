package fr.istic.mmm.scinov.activities.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.EventDetail.MapNestedFragment;

public class FullMapFragment extends Fragment {

    public FullMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MapNestedFragment mapNestedFragment = new MapNestedFragment();

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainMapFrame, mapNestedFragment)
                .addToBackStack(null)
                .commit();

        return inflater.inflate(R.layout.activity_map, container, false);
    }
}
