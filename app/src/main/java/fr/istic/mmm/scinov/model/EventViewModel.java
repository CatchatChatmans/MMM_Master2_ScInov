package fr.istic.mmm.scinov.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EventViewModel extends ViewModel {
    private static final DatabaseReference EVENT_REF =
            FirebaseDatabase.getInstance().getReference("/events");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(EVENT_REF);

    private final MediatorLiveData<List<Event>> eventsLiveData = new MediatorLiveData<>();

    public EventViewModel() {
        eventsLiveData.addSource(liveData, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Event> events = new LinkedList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Event event = child.getValue(Event.class);
                                if(event.getRatings() != null){
                                    event.setAvgRating(event.getRatings().values().stream().mapToDouble(Double::doubleValue).average().orElse(0));
                                    event.setKey(child.getKey());
                                    Log.i("RATING",Double.toString(event.getAvgRating()));
                                }

                                events.add(event);
                            }
                            Log.i("YOLO EVENTS 0", Long.toString(events.size()));
                            eventsLiveData.postValue(events);
                        }
                    }).start();
                } else {
                    eventsLiveData.setValue(null);
                }
            }
        });
    }

    @NonNull
    public LiveData<List<Event>> getEventsLiveData() {
        return eventsLiveData;
    }

    public LiveData<List<Event>> getEventsLiveDataFiltered(LatLng latLng) {

        List<Event> filteredList = eventsLiveData.getValue().stream().filter(event -> !event.getGeolocation().isEmpty() &&  Double.compare(event.getGeolocation().get(0), latLng.latitude) == 0
                && Double.compare(event.getGeolocation().get(1), latLng.longitude) == 0 ).collect(Collectors.toList());
        MediatorLiveData<List<Event>> fakeLiveData = new MediatorLiveData<>();
        fakeLiveData.setValue(filteredList);
        return fakeLiveData;
    }

    public void setValue(Event event) {
        EVENT_REF.child(event.getKey()).setValue(event);
    }
}
