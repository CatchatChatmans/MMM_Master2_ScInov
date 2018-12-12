package fr.istic.mmm.scinov;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        textViewView = itemView.findViewById(R.id.text);
        imageView = itemView.findViewById(R.id.image);
    }

    public void bind(Event event){
        Log.i("lolilol","lol");
        textViewView.setText(event.getName());
        Picasso.get().load(event.getImageUrl()).into(this.imageView);

    }
}