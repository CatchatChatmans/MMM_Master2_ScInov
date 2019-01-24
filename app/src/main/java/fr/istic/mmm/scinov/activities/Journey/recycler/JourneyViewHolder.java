package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.JourneyDetailFragment;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.Journey;

public class JourneyViewHolder extends RecyclerView.ViewHolder{

    private TextView journeyTitle;
    private TextView journeyAuthor;
    private Switch shared;
    private Journey journey;

    public JourneyViewHolder(View itemView) {
        super(itemView);
        journeyTitle = itemView.findViewById(R.id.journeyTitle);
        journeyAuthor = itemView.findViewById(R.id.journeyAuthor);
        shared = itemView.findViewById(R.id.shared);

        itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            JourneyDetailFragment eventDetailFragment = JourneyDetailFragment.newInstance(journey);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, eventDetailFragment).addToBackStack(null).commit();
        });
    }

    public void bind(Journey journey){
        this.journey = journey;
        journeyTitle.setText(journey.getTitle());
        journeyAuthor.setText(journey.getAuthor());
        shared.setChecked(journey.getShared());
    }
}