package fr.istic.mmm.scinov.activities.Map;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.EventDetail.MapNestedFragment;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(findViewById(R.id.mapFrame) != null) {
            if(savedInstanceState != null) {
                return;
            }


            MapNestedFragment mapNestedFragment = new MapNestedFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.mapFrame, mapNestedFragment)
                    .commit();

        }
    }
}
