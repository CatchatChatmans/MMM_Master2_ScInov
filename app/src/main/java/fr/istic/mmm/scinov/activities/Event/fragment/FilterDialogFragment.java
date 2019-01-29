package fr.istic.mmm.scinov.activities.Event.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.helpers.Filter;

public class FilterDialogFragment extends DialogFragment {

    public static FilterDialogFragment newInstance(Filter filter) {
        FilterDialogFragment dialog = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("filter", filter);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ViewGroup filterForm = (ViewGroup) inflater.inflate(R.layout.dialog_filter, null);

        Filter filter = getArguments().getParcelable("filter");

        ((CheckBox) filterForm.findViewById(R.id.dialog_filter_checkbox_name)).setChecked(filter.isFilterByName());
        ((CheckBox) filterForm.findViewById(R.id.dialog_filter_checkbox_place)).setChecked(filter.isFilterByPlace());
        ((CheckBox) filterForm.findViewById(R.id.dialog_filter_checkbox_theme)).setChecked(filter.isFilterByTheme());
        ((CheckBox) filterForm.findViewById(R.id.dialog_filter_checkbox_description)).setChecked(filter.isFilterByDescription());
        ((CheckBox) filterForm.findViewById(R.id.dialog_filter_checkbox_keywords)).setChecked(filter.isFilterByKeyword());

        TextView datePicker = filterForm.findViewById(R.id.dialog_filter_date_picker);
        TextView dateChosen = filterForm.findViewById(R.id.dialog_filter_date_chosen);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                c.set(Calendar.HOUR_OF_DAY,0);
                c.set(Calendar.MINUTE,0);
                c.set(Calendar.SECOND,0);
                c.set(Calendar.MILLISECOND,0);
                c.setTimeZone(TimeZone.getTimeZone("GMT+2"));

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateChosen.setText(dayOfMonth + "/" + (month +1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();

//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        Log.i("FILTER_DIALOG", "onCreateDialog");

        builder.setTitle(R.string.dialog_filter_title)
                .setView(filterForm)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("FILTER_DATE", "onClick");
                        mListener.onDialogPositiveClick(dialog);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(dialog);
                    }
                });

        return builder.create();
    }



    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface FilterDialogListener {
        public void onDialogPositiveClick(DialogInterface dialog);
        public void onDialogNegativeClick(DialogInterface dialog);
    }

    FilterDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (FilterDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
