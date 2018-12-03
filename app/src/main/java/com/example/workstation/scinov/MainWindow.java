package com.example.workstation.scinov;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this.cities));
    }

    private void ajouterVilles() {
        cities.add(new MyObject("France","http://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg"));
        cities.add(new MyObject("Angleterre","http://www.traditours.com/images/Photos%20Angleterre/ForumLondonBridge.jpg"));
        cities.add(new MyObject("Allemagne","http://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg"));
        cities.add(new MyObject("Espagne","http://www.sejour-linguistique-lec.fr/wp-content/uploads/espagne-02.jpg"));
        cities.add(new MyObject("Italie","http://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg"));
        cities.add(new MyObject("Russie","http://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg"));
    }
}
