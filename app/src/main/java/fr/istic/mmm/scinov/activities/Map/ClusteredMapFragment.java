package fr.istic.mmm.scinov.activities.Map;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;
import java.util.Objects;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.EventDetail.EventDetailFragment;
import fr.istic.mmm.scinov.activities.Home.EventsFragment;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class ClusteredMapFragment extends Fragment {

    private static final String STATE_KEY_MAP_CAMERA = "CameraState";
    MapView mMapView;
    private GoogleMap googleMap;
    // Declare a variable for the cluster manager.
    private ClusterManager<EventsCluster> mClusterManager;
    private EventViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);


        return rootView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        mMapView = view.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (savedInstanceState == null){
            Log.d("YOLO", "YOLO - Carto créée");
            mMapView.getMapAsync(mMap -> {
                googleMap = mMap;
                googleMap.setMyLocationEnabled(false);
                buildFullMap();
            });
        }

    }

    private void buildFullMap() {

        LatLng eventLocation = new LatLng(45.77966, 3.08628);
        // Position the map.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 5));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this.getContext(), googleMap);
        mClusterManager.setOnClusterItemInfoWindowClickListener( eventsCluster -> {
            EventDetailFragment eventDetailFragment = EventDetailFragment.newInstance(eventsCluster.getEvent());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, eventDetailFragment).addToBackStack(null).commit();

        });

        mClusterManager.setOnClusterClickListener( cluster -> {
            if(googleMap.getCameraPosition().zoom < 12) return false;
            EventsFragment eventsFragment = EventsFragment.newInstance(cluster.getItems().iterator().next().getPosition());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, eventsFragment).addToBackStack(null).commit();
            return true;
        });

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
        googleMap.setOnInfoWindowClickListener(mClusterManager);


        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {
        List<Event> list = viewModel.getEventsLiveData().getValue();
        Objects.requireNonNull(list);
        list.forEach(event -> {
            if(event.getGeolocation() != null && !event.getGeolocation().isEmpty()) {
                EventsCluster offsetItem = new EventsCluster(event.getGeolocation().get(0), event.getGeolocation().get(1), event.getName(), event.getDescriptionShort(), event);
                mClusterManager.addItem(offsetItem);
            }
        });
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