package fr.istic.mmm.scinov.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Event> list;

    public MyAdapter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Event> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card_2,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Event event = list.get(position);
        myViewHolder.bind(event);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}