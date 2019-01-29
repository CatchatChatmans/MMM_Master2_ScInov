package fr.istic.mmm.scinov.activities.Map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import fr.istic.mmm.scinov.activities.Event.model.Event;

public class EventsCluster implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private final Event event;

    public EventsCluster(double lat, double lng, String title, String snippet, Event event) {
        this.event = event;
        this.mPosition = new LatLng(lat, lng);
        this.mTitle = title;
        this.mSnippet = snippet;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
