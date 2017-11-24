package com.example.android.san;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    Toast toast;
    String type, tiffintype;
    VegFragment vegFragment;
    NonVegFragment nonVegFragment;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private long back_pressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        actionBarSetup();
        vegFragment = new VegFragment();
        nonVegFragment = new NonVegFragment();
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        type = getIntent().getStringExtra("Type");
        tiffintype = getIntent().getStringExtra("TiffinType");

        Log.d("Type", type);
        Log.d("TiffinType", tiffintype);

        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        bundle.putString("TiffinType", tiffintype);

        vegFragment.setArguments(bundle);
        nonVegFragment.setArguments(bundle);



        tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(vegFragment, "Veg");
        adapter.addFragment(nonVegFragment, "NonVeg");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
/*
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            // need to cancel the toast here
            toast.cancel();
            // code for exit
            startActivity(new Intent(this, TabActivity.class));

        } else {

            toast = Toast.makeText(getBaseContext(), "Press once again to logout!", Toast.LENGTH_SHORT);
            toast.show();
        }
        back_pressed = System.currentTimeMillis();
    }*/

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void actionBarSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle("Yashodeep Academy");
            ab.setSubtitle("Home/Main");
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
