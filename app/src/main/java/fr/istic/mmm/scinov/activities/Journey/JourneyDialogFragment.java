package fr.istic.mmm.scinov.activities.Journey;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.activities.Journey.model.JourneyViewModel;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class JourneyDialogFragment extends DialogFragment {


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

        JourneyViewModel viewModel =   ViewModelProviders.of(getActivity()).get(JourneyViewModel.class);
        LiveData<List<Journey>> liveData = viewModel.getPrivateJourneysLiveData();
        List<Journey> journeyToAdd = new ArrayList<>();
        List<Journey> journeyToRemove = new ArrayList<>();

        Event event = getArguments().getParcelable("Event");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        liveData.observe(getActivity(), journeysData -> {
            builder.setTitle(R.string.dialog_journey_list_title);
            if(journeysData != null) {
                boolean[] checkedItems = new boolean[journeysData.size()];
                String[] journeyNames = new String[journeysData.size()];
                for(int i = 0; i< journeysData.size(); i++) {
                    Journey journey = journeysData.get(i);
                    if(journey.getEvents().contains(event.getId())) {
                        checkedItems[i] = true;
                    } else {
                        checkedItems[i] = false;
                    }

                    journeyNames[i] = journey.getName();

                }

                builder.setMultiChoiceItems(journeyNames, checkedItems, (dialog,which,checked) -> {

                    if(checked) {
                        journeyToRemove.remove(journeysData.get(which));
                        journeyToAdd.add(journeysData.get(which));

                    } else {
                        journeyToAdd.remove(journeysData.get(which));
                        journeyToRemove.add(journeysData.get(which));
                    }

                    checkedItems[which] = checked;
                });

            }


        });
        builder.setPositiveButton(R.string.dialog_ok, (dialog, id) -> {
            journeyToAdd.forEach(journey -> {
                journey.getEvents().add(event.getId());
                viewModel.setValue(journey);
            });
            journeyToRemove.forEach(journey -> {
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
