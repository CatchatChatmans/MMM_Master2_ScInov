package fr.istic.mmm.scinov.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Journey;
import fr.istic.mmm.scinov.model.JourneyViewModel;

public class TestJourneys extends Fragment {

    private JourneyViewModel viewModel;
    LiveData<List<Journey>> liveData;
    List<Journey> journeys;

    public TestJourneys() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_journeys, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(JourneyViewModel.class);

        liveData = viewModel.getJourneysLiveData();

        journeys = new ArrayList<>();

        liveData.observe(this, new Observer<List<Journey>>() {
            @Override
            public void onChanged(@Nullable List<Journey> eventsData) {
                if (eventsData != null) {
                    journeys = eventsData;

                    for(Journey journey : journeys){
                        Log.i("JOURNEY",journey.toString());
                    }
                }
            }
        });

    }
}
