package com.example.android.san;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GoToCart extends AppCompatActivity {
    @InjectView(R.id.Listmenu)
    RecyclerView list_tiffin;
    @InjectView(R.id.btn_addTiffin)
    Button btnAddToCart;
    @InjectView(R.id.btn_continue)
    Button btnContinue;
    @InjectView(R.id.img_back)
    ImageView imageView;
    AdapterCart adapter;
    List<DataCart> data;
    SharedPreferences sp;
    String id, auth_Id, tiffin_plan, tiffin_Type, indian_Bread, rice, dal, price, quantity, menu;
    Bundle b;
    UrlRequest urlRequest;
    SharedPreferences.Editor editor;
    JSONArray jArray;
    JSONObject json_data;
    boolean login;
    int cartCount;
    ImageView imageEmptyCart;
    TextView textEmptyCart;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_cart);
        ButterKnife.inject(this);
        final List<DataCart> data = new ArrayList<>();
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        login = sp.getBoolean("LOGIN", false);
        auth_Id = sp.getString("AUTH_ID", "");
        Log.d("LOGIN", login + "");
        //auth_Id = getIntent().getStringExtra("Auth_Id");
        Log.d("AUTH111", auth_Id);

        if (login) {

            urlRequest = UrlRequest.getObject();
            urlRequest.setContext(GoToCart.this);
            urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_Id);
            Log.d("getDataURL: ", "http://sansmealbox.com/admin/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_Id);
            try {
                urlRequest.getResponse(new ServerCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("ResponseGOTO", response);
                        if (!response.contains("nodata")) {
                            try {

                                jArray = new JSONArray(response);

                                for (int i = 0; i < jArray.length(); i++) {
                                    Log.d("JarrayLength", jArray.length() + "");
                                    json_data = jArray.getJSONObject(i);
                                    DataCart tiffin_data = new DataCart();
                                    tiffin_data.id = json_data.getString("id");
                                    tiffin_data.tiffin_plan = json_data.getString("tiffinPlan");
                                    tiffin_data.tiffin_type = json_data.getString("tiffinType");
                                    tiffin_data.indian_bread = json_data.getString("indianBread");
                                    tiffin_data.rice = json_data.getString("rice");
                                    tiffin_data.dal = json_data.getString("dal");
                                    tiffin_data.price = json_data.getString("price");
                                    tiffin_data.quantity = json_data.getString("quantity");
                                    cartCount = cartCount + Integer.parseInt(tiffin_data.quantity);
                                    tiffin_data.menu = json_data.getString("menu");
                                    tiffin_data.deliveryDay = json_data.getString("deliveryDay");
                                    tiffin_data.totalCartItems = jArray.length();
                                    data.add(tiffin_data);
                                    Log.d(data.toString(), "data");
                                    Log.d("Price", tiffin_data.price);
                                }
                                editor = sp.edit();
                                editor.putString("CartCount", cartCount + "");
                                Log.d("ccount ", cartCount + "");
                                editor.commit();
                                list_tiffin.setVisibility(View.VISIBLE);
                                adapter = new AdapterCart(GoToCart.this, data);
                                list_tiffin.setAdapter(adapter);
                                LinearLayoutManager llm = new LinearLayoutManager(GoToCart.this);
                                list_tiffin.setLayoutManager(llm);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        } else {

                            editor = sp.edit();
                            editor.putString("CartCount", 0 + "");
                            editor.commit();
                            findViewById(R.id.relative1).setVisibility(View.GONE);
                            linearLayout = findViewById(R.id.linear1);
                            linearLayout.setVisibility(View.VISIBLE);
                            ImageView imageEmptyCart = findViewById(R.id.iv_nocart);
                            // TextView textEmptyCart = findViewById(R.id.textViewError);
                            Animation animation = AnimationUtils.loadAnimation(GoToCart.this, R.anim.shake);
                            imageEmptyCart.setAnimation(animation);
                            btnContinue.setText("Go Back");
                            btnContinue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onBackPressed();
                                }
                            });

                        }
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity a = GoToCart.this;
                    a.recreate();
                    editor = sp.edit();
                    editor.putString("AUTH_ID", auth_Id);
                    editor.putBoolean("LOGIN", true);
                    editor.commit();
                    Intent intent = new Intent(GoToCart.this, payment.class);
                    intent.putExtra("PARENT_ACTIVITY_NAME", "GoToCart");
                    startActivity(intent);
                    finish();
                }
            });
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor = sp.edit();
                    editor.putBoolean("LOGIN", true);
                    editor.putString("AUTH_ID", auth_Id);
                    editor.commit();
                    Intent intent = new Intent(GoToCart.this, MenuTypeTab.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Intent intentlogin = new Intent(GoToCart.this, LoginActivity.class);
            intentlogin.putExtra("PARENT_ACTIVITY_NAME", "GotoCart");
            finish();
            startActivity(intentlogin);
        }
    }

    @OnClick({R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        editor = sp.edit();
        editor.putBoolean("LOGIN", true);
        editor.putString("AUTH_ID", auth_Id);
        editor.commit();
        Intent intent = new Intent(GoToCart.this, MenuTypeTab.class);
        startActivity(intent);
        finish();

    }
}


