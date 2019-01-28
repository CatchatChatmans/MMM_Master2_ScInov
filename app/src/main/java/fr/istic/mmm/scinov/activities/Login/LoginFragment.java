package fr.istic.mmm.scinov.activities.Login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.mmm.scinov.R;

public class LoginFragment extends Fragment {

    private static final int NUM_ITEMS = 2;
    private FragmentPagerAdapter adapter;
    private ViewPager pager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_login);
        adapter = new FixedTabsPagerAdapter(getChildFragmentManager());

        pager = view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
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
                    return new SignInNestedFragment();
                case 1:
                    return new SignUpNestedFragment();
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return getString(R.string.sign_in_page_title);
                case 1:
                    return getString(R.string.sign_up_page_title);
                default:
                    return null;
            }
        }
    }
}
