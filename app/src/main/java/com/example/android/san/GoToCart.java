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
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GoToCart extends AppCompatActivity {
    @InjectView(R.id.Listmenu)
    RecyclerView list_tiffin;
    @InjectView(R.id.btn_addTiffin)
    Button btnAddToCart;
    @InjectView(R.id.btn_continue)
    Button btnContinue;
    AdapterCart adapter;
    List<DataCart> data;
    SharedPreferences sp;
    String id, auth_Id, tiffin_plan, tiffin_Type, indian_Bread, rice, dal, price, quantity, menu;
    Bundle b;
    UrlRequest urlRequest;
    SharedPreferences.Editor editor;
    JSONArray jArray;
    JSONObject json_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_cart);
        ButterKnife.inject(this);
        final List<DataCart> data = new ArrayList<>();
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        auth_Id = getIntent().getStringExtra("AUTH_ID");
        Log.d("AUTH_ID", auth_Id);

        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(GoToCart.this);
        urlRequest.setUrl("http://192.168.0.22:8001/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_Id);
        Log.d("getDataURL: ", "http://192.168.0.22:8001/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_Id);
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
                            tiffin_data.menu = json_data.getString("menu");
                            data.add(tiffin_data);
                            Log.d(data.toString(), "data");
                        }
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

                    findViewById(R.id.textViewError).setVisibility(View.VISIBLE);
                    findViewById(R.id.Listmenu).setVisibility(View.INVISIBLE);
                    btnContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(GoToCart.this, "Cart is empty", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GoToCart.this, "Go to payment activity ", Toast.LENGTH_LONG).show();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GoToCart.this, MenuTypeTab.class);
                startActivity(intent);
                finish();
            }
        });

    }
    
    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(GoToCart.this, TabActivity.class);
        startActivity(intent);
        finish();
    }*/
}


