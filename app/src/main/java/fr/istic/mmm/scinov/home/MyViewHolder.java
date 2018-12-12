package fr.istic.mmm.scinov.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.eventdetail.EventDetailActivity;
import fr.istic.mmm.scinov.model.Event;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private ImageView imageView;
    private Event event;

    public MyViewHolder(View itemView) {
        super(itemView);
        textViewView = itemView.findViewById(R.id.text);
        imageView = itemView.findViewById(R.id.image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent viewDetail = new Intent(v.getContext(), EventDetailActivity.class);
                viewDetail.putExtra("Event", event);
                v.getContext().startActivity(viewDetail);
            }
        });
    }

    public void bind(Event event){
        this.event = event;
        textViewView.setText(event.getName());
        Picasso.get().load(event.getImageUrl()).into(this.imageView);

    }
}