package fr.istic.mmm.scinov.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import fr.istic.mmm.scinov.R;
import fr.istic.mmm.scinov.fragment.SignInFragment;
import fr.istic.mmm.scinov.fragment.SignUpFragment;

public class SignIn extends AppCompatActivity {

    private static final int NUM_ITEMS = 2;
    private FragmentPagerAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        adapter = new FixedTabsPagerAdapter(getSupportFragmentManager());

        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
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
                    return new SignInFragment();
                case 1:
                    return new SignUpFragment();
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
