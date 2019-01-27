package fr.istic.mmm.scinov.activities.Journey.list;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.widget.ProgressBar;

import java.util.List;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Journey.recycler.JourneyListAdapter;
import fr.istic.mmm.scinov.helpers.MyUtil;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.activities.Journey.model.JourneyViewModel;

public class PrivateJourneyFragment extends Fragment {

    private RecyclerView recyclerView;
    final JourneyListAdapter adapter = new JourneyListAdapter(null);
    private JourneyViewModel viewModel;
    private String currentSearchQuery;
    LiveData<List<Journey>> liveData;

    public PrivateJourneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_private_journey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //get the view model from the main activity to avoid reloading the data
        viewModel = ViewModelProviders.of(getActivity()).get(JourneyViewModel.class);

        liveData = viewModel.getPrivateJourneysLiveData();

        final ProgressBar progressBar = view.findViewById(R.id.journeys_progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        liveData.observe(this, new Observer<List<Journey>>() {
            @Override
            public void onChanged(@Nullable List<Journey> journeysData) {
                if (journeysData != null) {
                    progressBar.setVisibility(View.GONE);
                    if(currentSearchQuery == null || currentSearchQuery.isEmpty()) {

                        adapter.setList(journeysData);
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
        MenuItem menuSearchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuSearchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        adapter.setMenuSearchItem(menuSearchItem);
        MyUtil.clickOutsideToUnfocusSearch(getParentFragment().getActivity().findViewById(R.id.activity_drawer),menuSearchItem);

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
}
