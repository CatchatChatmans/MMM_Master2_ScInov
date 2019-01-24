package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.JourneyDetailFragment;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;

public class JourneyViewHolder extends RecyclerView.ViewHolder{

    private TextView journeyTitle;
    private TextView journeyAuthor;
    private TextView journeyNbEvents;
    private Switch published;
    private Journey journey;

    public JourneyViewHolder(View itemView) {
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
    }

    public void bind(Journey journey){
        this.journey = journey;
        journeyTitle.setText(journey.getName());
        journeyAuthor.setText(journey.getAuthor());
        journeyNbEvents.setText(journey.getEvents().size() + " events");
        published.setChecked(journey.getPublished());
    }
}