package fr.istic.mmm.scinov.model;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {
    private static final DatabaseReference EVENT_REF =
            FirebaseDatabase.getInstance().getReference("/events");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(EVENT_REF);

    private FirebaseQueryLiveData queriedLiveData;

    private final MediatorLiveData<List<Event>> eventsLiveData = new MediatorLiveData<>();

    private boolean isQueryActivated = false;

    public EventViewModel() {
        eventsLiveData.addSource(liveData, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Event> events = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                events.add(child.child("fields").getValue(Event.class));
                            }
                            Log.i("YOLO EVENTS", Long.toString(events.size()));
                            eventsLiveData.postValue(events);
                        }
                    }).start();
                } else {
                    eventsLiveData.setValue(null);
                }
            }
        });
    }

    private class Deserializer implements Function<DataSnapshot, List<Event>> {
        @Override
        public List<Event> apply(DataSnapshot input) {
            List<Event> events = new ArrayList<>();
            for (DataSnapshot child : input.getChildren()) {
                events.add(child.getValue(Event.class));
            }
            return events;
        }
    }

    @NonNull
    public LiveData<List<Event>> getEventsLiveData() {
        return eventsLiveData;
    }

    public void queryData(String query) {
        Query searchedQuery = EVENT_REF.orderByChild("fields/titre_fr").startAt(query).endAt(query + "\uf8ff");
        queriedLiveData = new FirebaseQueryLiveData(searchedQuery);

        Log.i("SEND QUERY", "change livedata");

        eventsLiveData.removeSource(liveData);

        eventsLiveData.addSource(queriedLiveData, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                Log.i("YOLO EVENTS", "ONCHANGED");
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Event> events = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                events.add(child.child("fields").getValue(Event.class));
                            }
                            Log.i("YOLO EVENTS", Long.toString(events.size()));
                            eventsLiveData.postValue(events);
                        }
                    }).start();
                } else {
                    Log.i("YOLO EVENTS", "NULL");
                    eventsLiveData.setValue(null);
                }
            }
        });

        isQueryActivated = true;
    }

    public void resetQuery(){
        if(isQueryActivated){
            eventsLiveData.removeSource(queriedLiveData);

            eventsLiveData.addSource(liveData, new Observer<DataSnapshot>() {
                @Override
                public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<Event> events = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    events.add(child.child("fields").getValue(Event.class));
                                }
                                Log.i("YOLO EVENTS", Long.toString(events.size()));
                                eventsLiveData.postValue(events);
                            }
                        }).start();
                    } else {
                        eventsLiveData.setValue(null);
                    }
                }
            });

            isQueryActivated = false;
        }
    }
}
