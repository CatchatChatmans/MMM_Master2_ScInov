package fr.istic.mmm.scinov.activities.Journey.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.activities.Home.EventsListAdapter;
import fr.istic.mmm.scinov.activities.Journey.model.Journey;
import fr.istic.mmm.scinov.activities.Journey.model.JourneyViewModel;
import fr.istic.mmm.scinov.model.Event;
import fr.istic.mmm.scinov.model.EventViewModel;

public class JourneyDetailFragment extends Fragment {

    private TextView name;
    private TextView author;
    private Switch isPublished;
    private ImageButton delete;
    private RecyclerView recyclerView;
    private final EventsListAdapter adapter = new EventsListAdapter(null);

    public JourneyDetailFragment() {
        // Required empty public constructor
    }

    public static JourneyDetailFragment newInstance(Journey journey) {

        JourneyDetailFragment eventDetailFragment = new JourneyDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("Journey", journey);
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journey_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.journeyName);
        author = view.findViewById(R.id.journeyAuthor);
        isPublished = view.findViewById(R.id.journeyPublished);
        recyclerView = view.findViewById(R.id.recyclerView);
        delete = view.findViewById(R.id.deleteJourney);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        EventViewModel eventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        JourneyViewModel journeyViewModel = ViewModelProviders.of(getActivity()).get(JourneyViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Journey journey = bundle.getParcelable("Journey");

            eventViewModel.getEventsLiveData().observe(this, new Observer<List<Event>>() {
                @Override
                public void onChanged(@Nullable List<Event> eventsData) {
                    if (eventsData != null) {
                        List<Event> events = eventsData.stream().filter(event -> journey.getEvents().contains(event.getId())).collect(Collectors.toList());
                        adapter.setList(events);
                    }
                }
            });

            name.setText(journey.getName());
            author.setText(journey.getAuthor());
            isPublished.setChecked(journey.getPublished());

            isPublished.setOnClickListener(isPublished -> {
                journey.setPublished(!journey.getPublished());
                journeyViewModel.setValue(journey);

            });

            delete.setOnClickListener(v -> {
                journeyViewModel.deleteJourney(journey);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                JourneyListFragment journeyListFragment = new JourneyListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, journeyListFragment).addToBackStack(null).commit();
            });

        }

    }
}
