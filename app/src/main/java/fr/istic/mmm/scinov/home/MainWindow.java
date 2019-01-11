package fr.istic.mmm.scinov.home;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.SearchView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class MainWindow extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Event> events = new ArrayList<>();
    final MyAdapter adapter = new MyAdapter();
    private EventViewModel viewModel;
    LiveData<List<Event>> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        liveData = viewModel.getEventsLiveData();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.queryData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }


        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                viewModel.resetQuery();
                Log.i("SEARCHED CLOSED", "1");
                return false;
            }
        });

        return true;
    }


}
