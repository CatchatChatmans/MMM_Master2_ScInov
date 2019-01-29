package fr.istic.mmm.scinov.activities.Event.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Event.model.Event;
import fr.istic.mmm.scinov.activities.Event.model.EventViewModel;
import fr.istic.mmm.scinov.activities.Event.recycler.EventsListAdapter;
import fr.istic.mmm.scinov.helpers.MyUtil;

public class EventsFragment extends Fragment implements FilterDialogFragment.FilterDialogListener {
    private RecyclerView recyclerView;
    private List<Event> events = new ArrayList<>();
    final EventsListAdapter adapter = new EventsListAdapter(null);
    private EventViewModel viewModel;
    private String currentSearchQuery;
    FilterDialogFragment filterDialog;
    LiveData<List<Event>> liveData;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(LatLng latlng) {
        EventsFragment eventFragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putParcelable("SearchLatLng", latlng);
        eventFragment.setArguments(args);
        return eventFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        filterDialog = FilterDialogFragment.newInstance(adapter.getFilter());

        //get the view model from the main activity to avoid reloading the data
        viewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);

        if(getArguments() != null) {
            liveData = viewModel.getEventsLiveDataFiltered(getArguments().getParcelable("SearchLatLng") );
        } else {
            liveData = viewModel.getEventsLiveData();
        }

        final ProgressBar progressBar = view.findViewById(R.id.events_progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        liveData.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> eventsData) {
                if (eventsData != null) {
                    progressBar.setVisibility(View.GONE);
                    events = eventsData;
                    Log.i("SEARCHING","setList adapter " + events.size());
                    Log.i("SEARCHING","current query " + currentSearchQuery);
                    if(currentSearchQuery == null || currentSearchQuery.isEmpty()) {
                        Log.i("SEARCHING","FIRST INIT " + currentSearchQuery);
                        adapter.setList(events);
                    }
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuSearchItem =  menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuSearchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

//        TextView textView = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        textView.setTextColor(Color.WHITE);
//        textView.setHintTextColor(Color.WHITE);

        adapter.setMenuSearchItem(menuSearchItem);
        MyUtil.clickOutsideToUnfocusSearch(getActivity().findViewById(R.id.activity_drawer),menuSearchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.filter(query);
                if(!query.equals(currentSearchQuery)){
                    recyclerView.smoothScrollToPosition(0);
                }
                currentSearchQuery = query;
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(searchView.getQuery().length() > 0){
                    adapter.filter("");
                }else{
                    searchView.clearFocus();
                    searchView.onActionViewCollapsed();
                }
                return true;
            }
        });

        if(currentSearchQuery != null && (!currentSearchQuery.isEmpty())){
            Log.i("SEARCHING",currentSearchQuery);
            searchView.setIconified(false);
            searchView.setQuery(currentSearchQuery, true); // fill in the search term by default
            searchView.clearFocus(); // close the keyboard on load
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filter:
                filterDialog.show(getChildFragmentManager(),"DIALOG_FILTER");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogInterface dialog) {
        CheckBox checkBoxName = ((Dialog)dialog).findViewById(R.id.dialog_filter_checkbox_name);
        CheckBox checkBoxPlace = ((Dialog)dialog).findViewById(R.id.dialog_filter_checkbox_place);
        CheckBox checkBoxThemes = ((Dialog)dialog).findViewById(R.id.dialog_filter_checkbox_theme);
        CheckBox checkBoxDescription = ((Dialog)dialog).findViewById(R.id.dialog_filter_checkbox_description);
        CheckBox checkBoxKeywords = ((Dialog)dialog).findViewById(R.id.dialog_filter_checkbox_keywords);
        TextView datePicked = ((Dialog)dialog).findViewById(R.id.dialog_filter_date_chosen);

        adapter.getFilter().setFilterByName(checkBoxName.isChecked());
        adapter.getFilter().setFilterByPlace(checkBoxPlace.isChecked());
        adapter.getFilter().setFilterByTheme(checkBoxThemes.isChecked());
        adapter.getFilter().setFilterByDescription(checkBoxDescription.isChecked());
        adapter.getFilter().setFilterByKeyword(checkBoxKeywords.isChecked());

        String dateString = datePicked.getText().toString();
        Date date;

        Log.i("FILTER_DATE", "onDialogPositiveClick");

        if(!dateString.isEmpty()){
            try{
                date = (new SimpleDateFormat("dd/mm/yyyy").parse(dateString));
                Log.i("DATE FILTER_DATE", date.toString());
                adapter.getFilter().setDate(date);
            }catch (Exception e){
                Log.i("EXCEPTION", e.getMessage());
            }
        }else{
            date = null;
        }

        adapter.filter(currentSearchQuery);
    }

    @Override
    public void onDialogNegativeClick(DialogInterface dialog) {

    }
}
