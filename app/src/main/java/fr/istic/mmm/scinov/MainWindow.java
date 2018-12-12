package fr.istic.mmm.scinov;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        recyclerView = findViewById(R.id.recyclerView);
        final MyAdapter adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        EventViewModel viewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        LiveData<List<Event>> liveData = viewModel.getEventsLiveData();

        liveData.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> eventsData) {
                if (eventsData != null) {
                    events = eventsData;
                    adapter.setList(events);
                }
            }
        });

    }


}
