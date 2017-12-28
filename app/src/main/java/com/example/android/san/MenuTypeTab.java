package com.example.android.san;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.san.Fragment.BreakFastFragment;
import com.example.android.san.Fragment.FixedFragment;
import com.example.android.san.Fragment.FlexibleFragment;
import com.example.android.san.Fragment.SemiFlexibleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MenuTypeTab extends AppCompatActivity {

    public TabLayout tabLayout;
    Toast toast;
    String type, tiffintype,price;
    FlexibleFragment flexibleFragment;
    SemiFlexibleFragment semiFlexibleFragment;
    FixedFragment fixedFragment;
    BreakFastFragment breakFastFragment;
    @InjectView(R.id.img_back)
    ImageView imageback;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private long back_pressed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_type_tab);

        ButterKnife.inject(MenuTypeTab.this);

        flexibleFragment = new FlexibleFragment();
        semiFlexibleFragment = new SemiFlexibleFragment();
        fixedFragment=new FixedFragment();
        breakFastFragment=new BreakFastFragment();

        viewPager = findViewById(R.id.pager);
        setupViewPager(viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        bundle.putString("TiffinType", tiffintype);
        bundle.putString("Price",price);

        flexibleFragment.setArguments(bundle);
        semiFlexibleFragment.setArguments(bundle);
        fixedFragment.setArguments(bundle);
        breakFastFragment.setArguments(bundle);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(Color.parseColor("#cdcdcd"), Color.parseColor("#cdcdcd"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                tabLayout.setTabTextColors(Color.parseColor("#cdcdcd"), Color.parseColor("#ffffff"));
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

        adapter.addFragment(flexibleFragment, "Flexible");
        adapter.addFragment(semiFlexibleFragment, "SemiFlexible");
        adapter.addFragment(fixedFragment,"Fixed");
        adapter.addFragment(breakFastFragment,"BreakFast");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @OnClick({R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                Intent intent = new Intent(MenuTypeTab.this, HomeActivity.class);
                startActivity(intent);
                break;
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
        public int getCount()      {
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