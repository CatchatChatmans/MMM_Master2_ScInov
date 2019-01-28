package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.fragment.JourneyDetailFragment;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.activities.Journey.model.JourneyViewModel;

public class JourneyListViewHolder extends RecyclerView.ViewHolder {

    private TextView journeyTitle;
    private TextView journeyAuthor;
    private TextView journeyNbEvents;
    private Switch published;
    private Journey journey;

    public JourneyListViewHolder(View itemView) {
        super(itemView);
        journeyTitle = itemView.findViewById(R.id.journeyName);
        journeyAuthor = itemView.findViewById(R.id.journeyAuthor);
        journeyNbEvents = itemView.findViewById(R.id.journey_nb_events);
        published = itemView.findViewById(R.id.journeyPublished);

        itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            JourneyDetailFragment eventDetailFragment = JourneyDetailFragment.newInstance(journey);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, eventDetailFragment).addToBackStack(null).commit();
        });

        published.setOnClickListener(isPublished -> {
            journey.setPublished(!journey.getPublished());
            JourneyViewModel journeyViewModel = ViewModelProviders.of((AppCompatActivity) itemView.getContext()).get(JourneyViewModel.class);
            journeyViewModel.setValue(journey);
        });
    }

    public void bind(Journey journey) {
        this.journey = journey;
        journeyTitle.setText(journey.getName());
        journeyAuthor.setText(journey.getAuthor());
        journeyNbEvents.setText(journey.getEvents().size() + " events");
        published.setChecked(journey.getPublished());
    }
}