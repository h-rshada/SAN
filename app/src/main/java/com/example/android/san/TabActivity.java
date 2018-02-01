package com.example.android.san;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TabActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Toast toast;
    String type, tiffintype, price;
    VegFragment vegFragment;
    NonVegFragment nonVegFragment;
    @InjectView(R.id.img_back)
    ImageView imageback;
    @InjectView(R.id.img_viewCart)
    ImageView viewCart;
    @InjectView(R.id.cartCount)
    TextView txtCartCount;
    String auth_id,cartCount;
    boolean login;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private long back_pressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        //actionBarSetup();
        ButterKnife.inject(this);
        vegFragment = new VegFragment();
        nonVegFragment = new NonVegFragment();
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        auth_id = sp.getString("AUTH_ID", "");
        cartCount = sp.getString("CartCount", "");
        Log.d("Login!!!", login + "");
        Log.d("Auth", auth_id);
        Log.d("CartCount", cartCount);
        txtCartCount.setText(cartCount);
        editor = sp.edit();
        type = getIntent().getStringExtra("Type");
        tiffintype = getIntent().getStringExtra("TiffinType");
        price = getIntent().getStringExtra("Price");
        Log.d("Type", type);
        Log.d("TiffinType", tiffintype);
        Log.d("priceTab", price);
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        bundle.putString("TiffinType", tiffintype);
        bundle.putString("Price", price);
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
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabActivity.this, GoToCart.class);
                // intent.putExtra("Auth_Id", auth_id);
                if (login) {
                    editor.putString("AUTH_ID", auth_id);
                    editor.putBoolean("LOGIN", true);
                    editor.commit();
                    finish();
                    startActivity(intent);
                } else {
                    editor.putBoolean("LOGIN", false);
                    editor.commit();
                    finish();
                    startActivity(intent);
                }

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

    @OnClick({R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
               /* Intent intent = new Intent(TabActivity.this, MenuTypeTab.class);
                startActivity(intent);*/
                break;
        }

    }

    public void refresh(View v) {
        Intent i = getIntent();
        finish();
        startActivity(i);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (login) {
            editor = sp.edit();
            editor.putBoolean("LOGIN", true);
            editor.putString("AUTH_ID", auth_id);
            editor.commit();
            TabActivity.this.finish();
        } else {
            editor = sp.edit();
            editor.putBoolean("LOGIN", false);
            editor.commit();
            TabActivity.this.finish();
        }
    }

    /*  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
      private void actionBarSetup() {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
              android.support.v7.app.ActionBar ab = getSupportActionBar();
              ab.setTitle("Yashodeep Academy");
              ab.setSubtitle("Home/Main");
          }
      }
  */
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

