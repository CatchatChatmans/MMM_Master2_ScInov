package fr.istic.mmm.scinov.activities.Event.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Event.model.Event;

public class MapNestedFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;

            googleMap.setMyLocationEnabled(false);

            Event event = (Event)getArguments().get("Event");
            buildMapForDetail(event);
        });

        return rootView;
    }





    private void buildMapForDetail(Event event) {
        Log.i("GeoLoc", "[ " + event.getGeolocation().get(0) + " , " + event.getGeolocation().get(1) + " ] ");

        // For dropping a marker at a point on the Map
        LatLng eventLocation = new LatLng(event.getGeolocation().get(0), event.getGeolocation().get(1));
        googleMap.addMarker(new MarkerOptions().position(eventLocation).title(event.getName()).snippet(event.getAddress()));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(eventLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}