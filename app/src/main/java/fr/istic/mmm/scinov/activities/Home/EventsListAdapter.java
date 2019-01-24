package fr.istic.mmm.scinov.activities.Home;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.helpers.MyUtil;
import fr.istic.mmm.scinov.model.Event;

public class EventsListAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<Event> list;
    private List<Event> listCopy;
    private boolean hasSearchBar;

    public EventsListAdapter(boolean hasSearchBar) {
        this.list = new LinkedList<>();
        Log.i("SEARCHING", "adapter created");
        this.hasSearchBar = hasSearchBar;
    }

    public void setList(List<Event> list) {
        this.list = new LinkedList<>(list);
        this.listCopy = new LinkedList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card,viewGroup,false);
        if(hasSearchBar){
            MyUtil.clickOutsideToUnfocusSearch(view,((Activity) viewGroup.getContext()).findViewById(R.id.search));
        }
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int position) {
        Event event = list.get(position);
        eventViewHolder.bind(event);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filter(String query) {
        list.clear();
        if(query.isEmpty()){
            list.addAll(listCopy);
        }else{
            query = query.toLowerCase();
            for(Event event: listCopy){
                if((event.getName() != null && event.getName().toLowerCase().contains(query))
                || (event.getDescription() != null && event.getDescription().toLowerCase().contains(query))
                || (event.getKeywords() != null && event.getKeywords().toLowerCase().contains(query))
                || (event.getTheme() != null && event.getTheme().toLowerCase().contains(query))){
                    list.add(event);
                }
            }
        }
        notifyDataSetChanged();
    }
}