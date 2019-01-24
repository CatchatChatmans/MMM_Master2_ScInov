package fr.istic.mmm.scinov.activities.Journey.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Home.EventsListAdapter;
import fr.istic.mmm.scinov.activities.Journey.recycler.JourneyListAdapter;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;
import fr.istic.mmm.scinov.model.Journey;
import fr.istic.mmm.scinov.model.JourneyViewModel;

public class PrivateJourneyFragment extends Fragment {

    private RecyclerView recyclerView;
    final JourneyListAdapter adapter = new JourneyListAdapter();
    private JourneyViewModel viewModel;
    private String currentSearchQuery;
    LiveData<List<Journey>> liveData;

    public PrivateJourneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_private_journey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //get the view model from the main activity to avoid reloading the data
        viewModel = ViewModelProviders.of(getActivity()).get(JourneyViewModel.class);

        liveData = viewModel.getJourneysLiveData();

        final ProgressBar progressBar = view.findViewById(R.id.events_progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        liveData.observe(this, new Observer<List<Journey>>() {
            @Override
            public void onChanged(@Nullable List<Journey> journeysData) {
                if (journeysData != null) {
                    progressBar.setVisibility(View.GONE);
                    if(currentSearchQuery == null || currentSearchQuery.isEmpty()) {

                        adapter.setList(journeysData);
                    }
                }
            }
        });
    }
}
