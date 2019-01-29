package fr.istic.mmm.scinov.activities.Event.recycler;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Event.fragment.EventDetailFragment;
import fr.istic.mmm.scinov.activities.Event.model.Event;

public class EventViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private TextView secondaryTextView;
    private ImageView imageView;
    private RatingBar ratingView;
    private Event event;

    public EventViewHolder(View itemView) {
        super(itemView);
        textViewView = itemView.findViewById(R.id.eventName);
        imageView = itemView.findViewById(R.id.eventImage);
        secondaryTextView = itemView.findViewById(R.id.secondaryText);
        ratingView = itemView.findViewById(R.id.ratingBar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                EventDetailFragment eventDetailFragment = EventDetailFragment.newInstance(event);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, eventDetailFragment, "EVENT_FRAGMENT").addToBackStack(null).commit();
            }
        });
    }

    public void bind(Event event){
        this.event = event;
        textViewView.setText(event.getName());
        String secondaryText = event.getSimpleType() + " • " + event.getCity() + " • " + new SimpleDateFormat("dd MMMM", Locale.FRENCH).format(event.getFormattedDates().get(0));
        if(event.getFormattedDates().size() > 1){
            secondaryText +=  " (+ " + (event.getFormattedDates().size() - 1) + " dates)";
        }
        secondaryTextView.setText(secondaryText);

        Log.i("RATING_VIEW_HOLDER",Double.toString(event.getAvgRating()));
        ratingView.setRating((float) event.getAvgRating());

        Picasso.get().load(event.getImageUrl()).placeholder(R.drawable.placeholder).into(this.imageView);

    }
}