package com.example.android.san;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.txtMenu3)
    TextView txtMenu3;
    @InjectView(R.id.txtTiffinPlan)
    TextView tiffinPlan;
    @InjectView(R.id.txtTiffinType)
    TextView tiffinType;
    @InjectView(R.id.txtMenu3)
    TextView txtMenu1;
    @InjectView(R.id.txtBread)
    TextView txtBread;
    @InjectView(R.id.txtRice)
    TextView txtRice;
    @InjectView(R.id.txtDal)
    TextView txtDal;
    @InjectView(R.id.txtPrice)
    TextView txtPrice;
    @InjectView(R.id.txtDeliveryDay)
    TextView txtDeliveryDay;
    @InjectView(R.id.btnOrder)
    Button btnOrder;
    @InjectView(R.id.img_back)
    ImageView imageView;
    RequestQueue requestQueue;
    Set menuset;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String auth_id = "",week_day;
    String bread, rice, dal, amtoil, oiltype, heat, salt, menu, menu1, type, typetext, tiffintype, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        auth_id = sp.getString("AUTH_ID", "");
        Log.d("AAAuth", auth_id);
        menuset = new HashSet<String>();
        type = sp.getString("TYPE", null);
        tiffintype = sp.getString("TIFFIN", null);
        price = sp.getString("PRICE", null);
        typetext = type.substring(0, 1).toUpperCase();
        Log.d("type", type);
        Log.d("Tiffin", tiffintype);

        editor = sp.edit();

        final JSONObject orderData = new JSONObject();
        try {
            orderData.put("tiffinPlan", tiffintype);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (tiffintype.equals("Basic")) {
            menu = sp.getString("BASIC", null);
            editor.putString("BASIC", null);
            editor.commit();
            txtMenu3.setText(menu);
            try {
                orderData.put("menu", menu);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.d("Menu", menu);
        } else if (tiffintype.equals("Heavy"))
        {
            editor.putInt("COUNT", 0);
            editor.commit();
            if (type.equals("semiFlexible")) {
               /* txtMenu3.setText("1." + sp.getString("SEMISTR1", null));
                txtMenu1.setVisibility(View.VISIBLE);
                txtMenu1.setText("2." + sp.getString("SEMISTR2", null));*/
                menuset.add(sp.getString("SEMISTR1", null));
                menuset.add(sp.getString("SEMISTR2", null));
                txtMenu3.setText(menuset+"");
                Log.d("onCreate: ", menuset.toString());
                try {
                    orderData.put("menu", menuset);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                menuset = sp.getStringSet("HEAVY", null);
                List listOfNames = new ArrayList(menuset);
                Log.d("----------->", listOfNames.size() + "");
                if (listOfNames.size() == 0) {
                    Toast.makeText(this, "Select atleast 2 sabjis", Toast.LENGTH_SHORT).show();
                }
                menu = listOfNames.get(0).toString();
                menu1 = listOfNames.get(1).toString();
                txtMenu3.setText(listOfNames + "");
                /*txtMenu1.setVisibility(View.VISIBLE);
                txtMenu1.setText("2." + menu1)*/
                Log.d("Adapterlist***", listOfNames.toString());
                Log.d("Menu**", menu);
                Log.d("Menu1**", menu1);
                Log.d("Menuset", menuset.toString());
                try {
                    orderData.put("menu", menuset);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        week_day = getIntent().getStringExtra("deliveryDay");
        bread = getIntent().getStringExtra("Bread");
        rice = getIntent().getStringExtra("Rice");
        dal = getIntent().getStringExtra("Dal");
        heat = getIntent().getStringExtra("Heat");
        salt = getIntent().getStringExtra("Salt");
        amtoil = getIntent().getStringExtra("AmtOil");
        oiltype = getIntent().getStringExtra("OilType");
        price = getIntent().getStringExtra("Price");
        Log.d("Bread", bread);
        Log.d("Rice", rice);
        Log.d("Dal", dal);
        Log.d("Heat", heat);
        Log.d("Salt", salt);
        Log.d("AmtOil", amtoil);
        Log.d("OilType", oiltype);
        Log.d( "DeliveryDay",week_day);
        try {
            orderData.put("indianBread", bread);
            orderData.put("rice", rice);
            orderData.put("dal", dal);
            orderData.put("price", price);
            orderData.put("quantity", "1");
            orderData.put("heat", heat);
            orderData.put("salt", salt);
            orderData.put("amountOil", amtoil);
            orderData.put("oilType", oiltype);
            orderData.put("auth_id", auth_id);
            orderData.put("deliveryDay", week_day);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if ((typetext + type.substring(1)).equals("SemiFlexible")) {
            tiffinType.setText("Semi Flexible");
            try {
                orderData.put("tiffinType", "Semi Flexible");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            tiffinType.setText(typetext + type.substring(1));
            try {
                orderData.put("tiffinType", typetext + type.substring(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        tiffinPlan.setText(tiffintype);
        txtBread.setText(bread);
        txtRice.setText(rice);
        txtPrice.setText(price);
        txtDal.setText(dal);
        txtDeliveryDay.setText(week_day);
        final JSONObject object=new JSONObject();
        try {
            JSONArray jArray=new JSONArray();
            jArray.put(orderData);
            object.put("jsonObject",jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("orderData:", object.toString());
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sp.edit();
                editor.putString("AUTH_ID", auth_id);
                editor.putBoolean("LOGIN", true);
                editor.commit();
                Intent intent = new Intent(OrderDetailsActivity.this, payment.class);
                intent.putExtra("PARENT_ACTIVITY_NAME", "ODA");
                intent.putExtra("OBJECT",object+"");
                startActivity(intent);
                finish();
              /*  requestQueue = Volley.newRequestQueue(OrderDetailsActivity.this);
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, "http://192.168.0.107:8001/routes/server/app/myOrder.rfa.php", object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("ResponseODA", response.toString());
                                      Toast.makeText(OrderDetailsActivity.this,"yup",Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error: ", error.getMessage());

                    }
                });
                requestQueue.add(jsonObjReq);*/
            }
        });
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
        super.onBackPressed();
        editor = sp.edit();
        editor.putString("AUTH_ID", auth_id);
        editor.putBoolean("LOGIN", true);
        editor.commit();
        Intent intent = new Intent(OrderDetailsActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    public void backspaceorderdetails(View v) {
        onBackPressed();
    }
}
