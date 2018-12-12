package fr.istic.mmm.scinov;

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

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {
    private static final DatabaseReference EVENT_REF =
            FirebaseDatabase.getInstance().getReference("/events");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(EVENT_REF);

    private final MediatorLiveData<List<Event>> eventsLiveData = new MediatorLiveData<>();

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
}
