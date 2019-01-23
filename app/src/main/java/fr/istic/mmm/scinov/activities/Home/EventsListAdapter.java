package fr.istic.mmm.scinov.activities.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;

public class EventsListAdapter extends RecyclerView.Adapter<EventViewHolder> {

    List<Event> list;

    public EventsListAdapter() {
        this.list = new LinkedList<>();
    }

    public void setList(List<Event> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card,viewGroup,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int position) {
        Event event = list.get(position);
        eventViewHolder.bind(event);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}