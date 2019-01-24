package fr.istic.mmm.scinov.activities.Journey.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.LinkedList;
import java.util.List;

import fr.istic.mmm.scinov.model.FirebaseQueryLiveData;

public class JourneyViewModel extends ViewModel {
    private static final DatabaseReference JOURNEY_REF =
            FirebaseDatabase.getInstance().getReference("/journeys");
    
    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(JOURNEY_REF);

    private final MediatorLiveData<List<Journey>> journeysLiveData = new MediatorLiveData<>();



    public JourneyViewModel() {

        loadJourneys(liveData);

    }

    @NonNull
    public LiveData<List<Journey>> getJourneysLiveData() {
        return journeysLiveData;
    }

    private void loadJourneys(FirebaseQueryLiveData liveData) {
        journeysLiveData.addSource(liveData, dataSnapshot -> {
            if (dataSnapshot != null) {
                new Thread(() -> {
                    List<Journey> journeys = new LinkedList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        journeys.add(child.getValue(Journey.class));
                    }
                    journeysLiveData.postValue(journeys);
                }).start();
            } else {
                journeysLiveData.setValue(null);
            }
        });
    }

}
