package fr.istic.mmm.scinov.activities.Journey.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.helpers.MyUtil;

public class JourneyListAdapter extends RecyclerView.Adapter<JourneyViewHolder> {

    private List<Journey> list;
    private List<Journey> listCopy;
    private MenuItem menuSearchItem;

    public JourneyListAdapter(MenuItem menuSearchItem) {
        this.list = new LinkedList<>();
        this.menuSearchItem = menuSearchItem;
     }

    public void setList(List<Journey> list) {
        this.list = new LinkedList<>(list);
        listCopy = new LinkedList<>(list);
        notifyDataSetChanged();
    }

    public void setMenuSearchItem(MenuItem menuSearchItem) {
        this.menuSearchItem = menuSearchItem;
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.journey_card,viewGroup,false);
        if(menuSearchItem != null){
            MyUtil.clickOutsideToUnfocusSearch(view, menuSearchItem);
        }
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