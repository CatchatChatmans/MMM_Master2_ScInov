package fr.istic.mmm.scinov.home;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.fragment.DetailFragment;
import fr.istic.mmm.scinov.fragment.MapFragment;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(findViewById(R.id.mapFrame) != null) {
            if(savedInstanceState != null) {
                return;
            }


            MapFragment mapFragment = new MapFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.mapFrame, mapFragment)
                    .commit();

        }
    }
}
