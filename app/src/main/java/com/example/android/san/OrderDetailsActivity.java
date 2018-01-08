package com.example.android.san;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
    @InjectView(R.id.btnOrder)
    Button btnOrder;
    RequestQueue requestQueue;
    Set menuset;
    String auth_id;
    SharedPreferences sp;
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
        //Log.d("orderprice: ",price);
        typetext = type.substring(0, 1).toUpperCase();
        Log.d("type", type);
        Log.d("Tiffin", tiffintype);

        final JSONObject orderData = new JSONObject();
        try {
            orderData.put("tiffinPlan", tiffintype);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (tiffintype.equals("Basic")) {
            menu = sp.getString("BASIC", null);
            txtMenu3.setText(menu);
            Log.d("Menu", menu);
        } else if (tiffintype.equals("Heavy"))
        {

            if (type.equals("semiFlexible")) {
                txtMenu3.setText("1." + sp.getString("SEMISTR1", null));
                txtMenu1.setVisibility(View.VISIBLE);
                txtMenu1.setText("2." + sp.getString("SEMISTR2", null));
                menuset.add(sp.getString("SEMISTR1", null));
                menuset.add(sp.getString("SEMISTR2", null));
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
                txtMenu3.setText("1." + menu);
                txtMenu1.setVisibility(View.VISIBLE);
                txtMenu1.setText("2." + menu1);
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
        try {
            orderData.put("IndianBread", bread);
            orderData.put("Rice", rice);
            orderData.put("Dal", dal);
            orderData.put("Price", price);
            orderData.put("Quantity", "1");
            orderData.put("Heat", heat);
            orderData.put("Salt", salt);
            orderData.put("AmountOfOil", amtoil);
            orderData.put("OilType", oiltype);
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
        Log.d("orderData:", orderData.toString());
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue = Volley.newRequestQueue(OrderDetailsActivity.this);
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, "http://192.168.0.107:8001/routes/server/app/addToCart.rfa.php", orderData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("ResponseOrder", response.toString());


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error: ", error.getMessage());

                    }
                });
                requestQueue.add(jsonObjReq);
            }
        });
    }

    public void backspaceorderdetails(View v) {
        onBackPressed();
    }
}
