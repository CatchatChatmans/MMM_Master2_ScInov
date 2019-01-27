package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.model.Event;

public class JourneySelectionAdapter extends RecyclerView.Adapter<JourneySelectionViewHolder> {

    private List<Journey> list;
    private List<Boolean> state;
    private List<Journey> journeysAdded;
    private List<Journey> journeysRemoved;
    private Event event;

    public JourneySelectionAdapter(Event event) {
        this.list = new ArrayList<>();
        this.state = new ArrayList<>();
        this.journeysAdded = new ArrayList<>();
        this.journeysRemoved = new ArrayList<>();
        this.event = event;
    }

    public void setList(List<Journey> list) {
        this.list = list;

        this.list.forEach(j -> state.add(j.getEvents().contains(event.getId())));

        notifyDataSetChanged();
    }


    @Override
    public JourneySelectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_journey_card_dialog, viewGroup, false);
        JourneySelectionViewHolder viewHolder = new JourneySelectionViewHolder(view);
        viewHolder.setAdapter(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(JourneySelectionViewHolder journeySelectionViewHolder, int position) {
        Journey journey = list.get(position);
        journeySelectionViewHolder.bind(journey, state.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setCheckedChange(Journey journey, boolean checked) {
        if (checked) {
            journeysRemoved.remove(journey);
            journeysAdded.add(journey);
        } else {
            journeysAdded.remove(journey);
            journeysRemoved.add(journey);
        }

        state.set(list.indexOf(journey), checked);
    }

    public List<Journey> getJourneysAdded() {
        return journeysAdded;
    }

    public List<Journey> getJourneysRemoved() {
        return journeysRemoved;
    }
}