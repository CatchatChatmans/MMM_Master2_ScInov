package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.fragment.JourneyDetailFragment;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.activities.Journey.model.JourneyViewModel;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class JourneyListViewHolder extends RecyclerView.ViewHolder {

    private TextView journeyTitle;
    private ImageView journeyPicPreview;
    private TextView journeyNbEvents;
    private Switch published;
    private Journey journey;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public JourneyListViewHolder(View itemView) {
        super(itemView);
        journeyTitle = itemView.findViewById(R.id.journeyName);
        journeyPicPreview = itemView.findViewById(R.id.journey_pic_preview);
        journeyNbEvents = itemView.findViewById(R.id.journey_nb_events);
        published = itemView.findViewById(R.id.journeyPublished);

        itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            JourneyDetailFragment eventDetailFragment = JourneyDetailFragment.newInstance(journey);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, eventDetailFragment).addToBackStack(null).commit();
        });
    }

    public void bind(Journey journey) {
        this.journey = journey;
        journeyTitle.setText(journey.getName());

        EventViewModel eventViewModel = ViewModelProviders.of((AppCompatActivity) itemView.getContext()).get(EventViewModel.class);

        eventViewModel.getEventsLiveData().observe((AppCompatActivity) itemView.getContext(), new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> eventsData) {
                if (eventsData != null) {
                    List<Event> events = eventsData.stream().filter(event -> journey.getEvents().contains(event.getId())).collect(Collectors.toList());
                    if(!events.isEmpty() && (events.get(0) != null) && (events.get(0).getImageUrl() != null) && (!events.get(0).getImageUrl().isEmpty()) ){
                        Picasso.get().load(events.get(0).getImageUrl()).placeholder(R.drawable.placeholder).into(journeyPicPreview);
                    }
                }
            }
        });

        journeyNbEvents.setText(journey.getEvents().size() + " events");
        published.setChecked(journey.getPublished());

        if(auth.getCurrentUser() != null && auth.getCurrentUser().getUid().equals(journey.getAuthor())) {
            published.setOnClickListener(isPublished -> {
                journey.setPublished(!journey.getPublished());
                JourneyViewModel journeyViewModel = ViewModelProviders.of((AppCompatActivity) itemView.getContext()).get(JourneyViewModel.class);
                journeyViewModel.setValue(journey);
            });
        }else{
            published.setEnabled(false);
        }
    }
}