package com.example.android.san;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.File;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    @InjectView(R.id.order)
    ImageView order;
    @InjectView(R.id.aboutus)
    ImageView aboutus;
    @InjectView(R.id.find)
    ImageView find;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean login;
    String auth_id;
    MenuItem menuItem;
    //    SliderLayout sliderLayout;
    HashMap<String, Integer> Hash_file_maps;
    private long back_pressed = 0;
    private Toast toast;

    private static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        Log.d("Login^^^^^^^6666", login + "");
        auth_id = sp.getString("AUTH_ID", "");
        Log.d("Authid1", auth_id);

//        sliderLayout = findViewById(R.id.slider);
//        Hash_file_maps=new HashMap<String, Integer>();
//
//        Hash_file_maps.put(".............", R.drawable.food1);
//        Hash_file_maps.put("...........", R.drawable.food2);
//        Hash_file_maps.put("....", R.drawable.food3);
//        for (String name : Hash_file_maps.keySet()) {
//
//            TextSliderView textSliderView = new TextSliderView(HomeActivity.this);
//            textSliderView
//                    //.description(name)
//                    .image(Hash_file_maps.get(name))
//                    /*.setScaleType(BaseSliderView.ScaleType.Fit)*/
//                    .setOnSliderClickListener(this);
//            //textSliderView.bundle(new Bundle());
//            //textSliderView.getBundle();
//                  /*  .putString("extra", name);*/
////            sliderLayout.addSlider(textSliderView);
//        }
        //sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // sliderLayout.setCustomAnimation(new DescriptionAnimation());
//        sliderLayout.setDuration(3000);
//        sliderLayout.addOnPageChangeListener(this);


        try {
            File f = new File("/data/data/com.xoxytech.ostello/shared_prefs/YourSharedPreference.xml");
            if (f.exists()) {
                Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
                SharedPreferences sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
                // String selectedsubji = sp.getString("SELECTEDSUBJI", null);
            } else
                Log.d("TAG", "Setup default preferences");

        } catch (Exception e) {
            e.printStackTrace();
        }

        ButterKnife.inject(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.shareicon));
        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon1 = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);
        ImageView itemIcon3 = new ImageView(this);
        ImageView itemIcon4 = new ImageView(this);
        int subActionButtonSize = 120;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(subActionButtonSize, subActionButtonSize);
        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.facebook));
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.twitter));
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.whatsapp));
        itemIcon4.setImageDrawable(getResources().getDrawable(R.drawable.linkedin));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).setLayoutParams(params).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).setLayoutParams(params).build();
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).setLayoutParams(params).build();
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).setLayoutParams(params).build();

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#ffffff"));
        gd.setShape(GradientDrawable.OVAL);
        button1.setBackground(gd);
        button2.setBackground(gd);
        button3.setBackground(gd);
        button4.setBackground(gd);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .setRadius(250)
                .attachTo(actionButton)
                .build();
        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {

                AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
                alpha.setDuration(0); // Make animation instant
                alpha.setFillAfter(true);
                findViewById(R.id.container).startAnimation(alpha);
                LinearLayout layout = findViewById(R.id.container1);
                setViewAndChildrenEnabled(layout, false);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                AlphaAnimation alpha = new AlphaAnimation(1.0F, 1.0F);
                alpha.setDuration(0); // Make animation instant
                alpha.setFillAfter(true);
                findViewById(R.id.container1).startAnimation(alpha);
                LinearLayout layout = findViewById(R.id.container1);
                setViewAndChildrenEnabled(layout, true);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.facebook.com/yashodeepacademy/"));
                startActivity(intent);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/Yashodeep2017"));
                startActivity(intent);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND).setData(Uri.parse("whatsapp://send?text=http://www.example.com"));
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.ydacademy.dell.Yashodeep2");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.linkedin.com/in/yashodeep-academy-b61232153/"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (back_pressed + 2000 > System.currentTimeMillis()) {
            // need to cancel the toast here
            // toast.cancel();
            // code for exit

        } else {
            // ask user to press back button one more time to close app


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
//            alertDialog.setTitle("Delete Tiffin....");
//            alertDialog.setIcon(R.drawable.removea);
            alertDialog.setMessage("Are you sure to Exit...");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(
                    "YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });


            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alertDialog.show();
        }
        back_pressed = System.currentTimeMillis();
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        menuItem = item;
        //noinspection SimplifiableIfStatement
       */
/* if (id == R.id.action_settings) {

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivityForResult(intent, 100);
            return true;
        }*//*


        return super.onOptionsItemSelected(item);
    }
*/

    @OnClick({R.id.order, R.id.aboutus, R.id.find})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.order:

                intent=new Intent(HomeActivity.this,MenuTypeTab.class);
                finish();
                startActivity(intent);
                break;

            case R.id.aboutus:

                intent = new Intent(HomeActivity.this, AboutUs.class);
                finish();
                startActivity(intent);
                break;

            case R.id.find:
                intent = new Intent(HomeActivity.this, MapsActivity.class);
                finish();
                startActivity(intent);
                break;
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_account) {
            editor = sp.edit();
            editor.putBoolean("LOGIN", login);
            editor.putString("AUTH_ID", auth_id);
            editor.commit();
            intent = new Intent(HomeActivity.this, Profile.class);
            finish();
            startActivity(intent);
        }

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(HomeActivity.this, ContactUs.class);
            finish();
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=com.xoxytech.ostello");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomeActivity.this, GoToCart.class);
            if (login) {
                editor = sp.edit();
                editor.putBoolean("LOGIN", login);
                Log.d("GOTOAUTH", auth_id);
                editor.putString("AUTH_ID", auth_id);
                editor.commit();
                startActivity(intent);
            } else {
                editor = sp.edit();
                editor.putBoolean("LOGIN", false);
                editor.commit();
                startActivity(intent);
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("data", true)) {
                menuItem.setTitle("Logout");
                NavigationView navigationView = findViewById(R.id.nav_view);

                Menu menu = navigationView.getMenu();
                menu.getItem(0).setTitle("Logout");
                Log.d("****", "Item**** ");
            }
        }
    }*/

    @Override
    protected void onStop() {

//        sliderLayout.stopAutoCycle();

        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}


