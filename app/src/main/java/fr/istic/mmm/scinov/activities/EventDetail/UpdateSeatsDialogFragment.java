package fr.istic.mmm.scinov.activities.EventDetail;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class UpdateSeatsDialogFragment extends DialogFragment {

    private EventViewModel viewModel;

    public static UpdateSeatsDialogFragment newInstance(Event event) {
        UpdateSeatsDialogFragment fragment = new UpdateSeatsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("Event", event);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Event event = getArguments().getParcelable("Event");
        viewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_update_seats_dialog, null);

        EditText seatsAvailable = view.findViewById(R.id.update_seats_available);
        EditText seatsTaken = view.findViewById(R.id.update_seats_taken);
        if(event.getSeatsAvailable() != 0){
            seatsAvailable.setText(Integer.toString(event.getSeatsAvailable()));
        }
        if(event.getSeatsTaken() != 0){
            seatsTaken.setText(Integer.toString(event.getSeatsTaken()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Mettez à jour le nombre de places");
        builder.setPositiveButton(R.string.dialog_ok, (dialog, id) -> {
            if(!seatsAvailable.getText().toString().isEmpty() && !seatsTaken.getText().toString().isEmpty()
                    && Integer.parseInt(seatsAvailable.getText().toString()) >= Integer.parseInt(seatsTaken.getText().toString())){
                event.setSeatsAvailable(Integer.parseInt(seatsAvailable.getText().toString()));
                event.setSeatsTaken(Integer.parseInt(seatsTaken.getText().toString()));
                viewModel.setValue(event);
            }
        })
                .setNegativeButton(R.string.dialog_cancel, (dialog, id) -> {
                    // User cancelled the dialog
                });
        return builder.create();
    }
}
