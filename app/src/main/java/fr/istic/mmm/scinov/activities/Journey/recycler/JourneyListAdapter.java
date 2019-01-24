package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.helpers.MyUtil;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;

public class JourneyListAdapter extends RecyclerView.Adapter<JourneyViewHolder> {

    List<Journey> list;
    List<Journey> listCopy;

    public JourneyListAdapter() {
        this.list = new LinkedList<>();
     }

    public void setList(List<Journey> list) {
        this.list = new LinkedList<>(list);
        listCopy = new LinkedList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.journey_card,viewGroup,false);
        MyUtil.clickOutsideToUnfocusSearch(view,((Activity) viewGroup.getContext()).findViewById(R.id.search));
        return new JourneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JourneyViewHolder journeyViewHolder, int position) {
        Journey journey = list.get(position);
        journeyViewHolder.bind(journey);
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
            for(Journey journey: listCopy){
                if(journey.getName() != null && journey.getName().toLowerCase().contains(query)) {
                    list.add(journey);
                }
            }
        }
        notifyDataSetChanged();
    }
}