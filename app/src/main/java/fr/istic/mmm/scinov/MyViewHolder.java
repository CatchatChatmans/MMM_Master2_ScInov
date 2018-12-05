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
        textViewView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(MyObject myObject){
        Log.i("lolilol","lol");
        textViewView.setText(myObject.getText());
        Picasso.get().load(myObject.getImageUrl()).into(this.imageView);

    }
}