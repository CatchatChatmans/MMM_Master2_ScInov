package fr.istic.mmm.scinov;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<MyObject> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        ajouterVilles();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final MyAdapter adapter = new MyAdapter(this.cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        EventViewModel viewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    //update the values of the adapter
                }
            }
        });

    }

    private void ajouterVilles() {
        cities.add(new MyObject("France","https://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg"));
        cities.add(new MyObject("Angleterre","https://www.traditours.com/images/Photos%20Angleterre/ForumLondonBridge.jpg"));
        cities.add(new MyObject("Allemagne","https://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg"));
        cities.add(new MyObject("Espagne","https://www.sejour-linguistique-lec.fr/wp-content/uploads/espagne-02.jpg"));
        cities.add(new MyObject("Italie","https://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg"));
        cities.add(new MyObject("Russie","https://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg"));
    }
}
