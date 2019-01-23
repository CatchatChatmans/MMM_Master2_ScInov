package fr.istic.mmm.scinov.activities.EventDetail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Map.EventsCluster;
import fr.istic.mmm.scinov.model.Event;

public class MapNestedFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    // Declare a variable for the cluster manager.
    private ClusterManager<EventsCluster> mClusterManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                googleMap.setMyLocationEnabled(false);


                if(getArguments() != null) {
                    Event event = (Event)getArguments().get("Event");
                    buildMapForDetail(event);
                } else {
                    buildFullMap();
                }
            }
        });

        return rootView;
    }





    private void buildMapForDetail(Event event) {
        Log.i("GeoLoc", "[ " + event.getGeolocation().get(0) + " , " + event.getGeolocation().get(1) + " ] ");

        // For dropping a marker at a point on the Map
        LatLng eventLocation = new LatLng(event.getGeolocation().get(0), event.getGeolocation().get(1));
        googleMap.addMarker(new MarkerOptions().position(eventLocation).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(eventLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void buildFullMap() {
        setUpClusterer();
//        LatLng eventLocation = new LatLng(48.11198, -1.67429);
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(eventLocation).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setUpClusterer() {
        LatLng eventLocation = new LatLng(48.11198, -1.67429);
        // Position the map.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this.getContext(), googleMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 48.11198;
        double lng = -1.67429;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            EventsCluster offsetItem = new EventsCluster(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
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