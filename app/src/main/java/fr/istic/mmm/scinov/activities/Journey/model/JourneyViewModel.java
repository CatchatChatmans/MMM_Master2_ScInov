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

    private final Query queryPrivateJourneys = JOURNEY_REF;
    private final Query queryPublicJourneys = JOURNEY_REF.orderByChild("isPublished").equalTo(true);

    private FirebaseQueryLiveData liveDataPrivateJourneys = new FirebaseQueryLiveData(queryPrivateJourneys);
    private FirebaseQueryLiveData liveDataPublicJourneys = new FirebaseQueryLiveData(queryPublicJourneys);

    private final MediatorLiveData<List<Journey>> privateJourneysLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<List<Journey>> publicJourneysLiveData = new MediatorLiveData<>();

    public JourneyViewModel() {
        loadJourneys(liveDataPrivateJourneys, privateJourneysLiveData);
        loadJourneys(liveDataPublicJourneys, publicJourneysLiveData);
    }

    @NonNull
    public LiveData<List<Journey>> getPrivateJourneysLiveData() {
        return privateJourneysLiveData;
    }

    @NonNull
    public LiveData<List<Journey>> getPublicJourneysLiveData() {
        return publicJourneysLiveData;
    }

    private void loadJourneys(FirebaseQueryLiveData liveData, MediatorLiveData<List<Journey>> mediatorLiveData) {
        mediatorLiveData.addSource(liveData, dataSnapshot -> {
            if (dataSnapshot != null) {
                new Thread(() -> {
                    List<Journey> journeys = new LinkedList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        journeys.add(child.getValue(Journey.class));
                    }
                    mediatorLiveData.postValue(journeys);
                }).start();
            } else {
                mediatorLiveData.setValue(null);
            }
        });
    }

}
