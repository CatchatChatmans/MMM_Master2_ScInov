package fr.istic.mmm.scinov;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventViewModel extends ViewModel {
    private static final DatabaseReference EVENT_REF =
            FirebaseDatabase.getInstance().getReference("/events");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(EVENT_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}
