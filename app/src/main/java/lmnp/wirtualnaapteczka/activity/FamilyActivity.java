package lmnp.wirtualnaapteczka.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.TabContacts;
import lmnp.wirtualnaapteczka.TabMedicinesFamilyList;
import lmnp.wirtualnaapteczka.TabWaiting;

/**
 * Created by Jowita on 2018-01-06.
 */

public class FamilyActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    TabMedicinesFamilyList t1 = new TabMedicinesFamilyList();
                    return t1;
                case 1:
                    TabContacts t2 = new TabContacts();
                    return t2;
                case 2:
                    TabWaiting t3 = new TabWaiting();
                    return t3;

                default:
                    return null;


            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.medicines_my_family);
                case 1:
                    return getString(R.string.contacts);
                case 2:
                    return getString(R.string.waiting);
            }
            return null;
        }
    }
}
