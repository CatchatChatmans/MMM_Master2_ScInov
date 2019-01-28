package fr.istic.mmm.scinov.activities.Journey.fragment;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.activities.Journey.model.JourneyViewModel;
import fr.istic.mmm.scinov.activities.Journey.recycler.JourneySelectionAdapter;
import fr.istic.mmm.scinov.model.Event;

public class JourneyDialogFragment extends DialogFragment {


    private RecyclerView recyclerView;
    private JourneySelectionAdapter adapter;
    private JourneyViewModel viewModel;
    private LiveData<List<Journey>> liveData;

    public JourneyDialogFragment() {
        // Required empty public constructor
    }

    public static JourneyDialogFragment newInstance(Event event) {
        JourneyDialogFragment fragment = new JourneyDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("Event", event);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Event event = getArguments().getParcelable("Event");
        viewModel = ViewModelProviders.of(getActivity()).get(JourneyViewModel.class);
        liveData = viewModel.getPrivateJourneysLiveData();
        adapter = new JourneySelectionAdapter(event);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_journey_dialog, null);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        final ProgressBar progressBar = view.findViewById(R.id.journeys_progress_bar);

        progressBar.setVisibility(View.VISIBLE);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.dialog_journey_list_title);

        liveData.observe(getActivity(), journeysData -> {

            progressBar.setVisibility(View.INVISIBLE);

            adapter.setList(journeysData);

        });
        builder.setPositiveButton(R.string.dialog_ok, (dialog, id) -> {
            adapter.getJourneysAdded().forEach(journey -> {
                Log.i("YOLO", "Adding event: " + event.getId() + " from journey: " + journey.getKey());
                journey.getEvents().add(event.getId());
                viewModel.setValue(journey);
            });
            adapter.getJourneysRemoved().forEach(journey -> {
                Log.i("YOLO", "Removing event: " + event.getId() + " from journey: " + journey.getKey());
                journey.getEvents().remove(event.getId());
                viewModel.setValue(journey);
            });

        })
                .setNegativeButton(R.string.dialog_cancel, (dialog, id) -> {
                    // User cancelled the dialog
                });
        return builder.create();

    }


}
