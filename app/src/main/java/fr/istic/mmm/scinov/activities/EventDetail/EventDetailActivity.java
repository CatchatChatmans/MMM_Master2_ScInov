package fr.istic.mmm.scinov.activities.EventDetail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import fr.istic.mmm.scinov.R;

public class EventDetailActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        if(findViewById(R.id.detailFrame) != null) {
            if(savedInstanceState != null) {
                return;
            }

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());

            MapFragment mapFragment = new MapFragment();
            mapFragment.setArguments(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
            .add(R.id.detailFrame, detailFragment)
            .add(R.id.mapFrame, mapFragment)
            .commit();

        }
    }

}
