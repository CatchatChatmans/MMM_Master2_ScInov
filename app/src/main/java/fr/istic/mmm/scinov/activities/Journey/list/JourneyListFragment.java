package fr.istic.mmm.scinov.activities.Journey.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;

public class JourneyListFragment extends Fragment {

    private static final int NUM_ITEMS = 2;
    private FragmentPagerAdapter adapter;
    private ViewPager pager;

    public JourneyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        adapter = new JourneyListFragment.FixedTabsPagerAdapter(getChildFragmentManager());

        pager = view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_journey_list, container, false);
    }

    public class FixedTabsPagerAdapter extends FragmentPagerAdapter{

        public FixedTabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0:
                    return new PrivateJourneyFragment();
                case 1:
                    return new PublicJourneyFragment();
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return getString(R.string.private_journeys_pager_title);
                case 1:
                    return getString(R.string.shared_journeys_pager_title);
                default:
                    return null;
            }
        }
    }



}
