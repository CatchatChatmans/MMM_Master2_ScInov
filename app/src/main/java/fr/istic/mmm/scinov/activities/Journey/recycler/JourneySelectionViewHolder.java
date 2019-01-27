package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;

public class JourneySelectionViewHolder extends RecyclerView.ViewHolder {

    private TextView journeyName;
    private CheckBox selected;
    private Journey journey;
    private JourneySelectionAdapter adapter;


    public JourneySelectionViewHolder(View itemView) {
        super(itemView);
        journeyName = itemView.findViewById(R.id.journeyName);
        selected = itemView.findViewById(R.id.journeyIsChecked);

        selected.setOnCheckedChangeListener((button, checked) -> {
            adapter.setCheckedChange(journey, checked);
        });
    }

    public void bind(Journey journey, boolean checked) {
        this.journey = journey;
        journeyName.setText(journey.getName());
        selected.setChecked(checked);
    }

    public void setAdapter(JourneySelectionAdapter adapter) {
        this.adapter = adapter;
    }
}