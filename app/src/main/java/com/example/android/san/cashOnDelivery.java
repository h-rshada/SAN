package com.example.android.san;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class cashOnDelivery extends AppCompatActivity {
    @InjectView(R.id.edt_name)
    EditText edtName;
    @InjectView(R.id.edt_address)
    EditText edtAddress;
    @InjectView(R.id.edt_phone)
    EditText edtPhone;
    @InjectView(R.id.edt_email)
    EditText edtEmail;
    @InjectView(R.id.btn_order)
    Button btnOrder;
    SharedPreferences sp;
    boolean login;
    String auth_id = "";
    UrlRequest urlRequest;
    JSONArray jArray;
    JSONObject json_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        auth_id = sp.getString("AUTH_ID", "");

        Log.d("Login^^^^^^^6666", login + "");
        Log.d("Auth_id", auth_id);
        Log.d("I m in cashon delivery", "Cash");
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(cashOnDelivery.this);
        Log.d("URL", "http://192.168.0.107:8001/routes/server/app/getAddress.rfa.php?AuthId=" + auth_id);
        urlRequest.setUrl("http://192.168.0.107:8001/routes/server/app/getAddress.rfa.php?AuthId=" + auth_id);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("ResponseCOD", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    edtName.setText(jsonObject.getString("name"));
                    edtAddress.setText(jsonObject.getString("address"));
                    edtPhone.setText(jsonObject.getString("phone"));
                    edtEmail.setText(jsonObject.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                // Log.d( "Time",currentDateandTime);
                final String timeAndDate[] = currentDateandTime.split(" ");
                Log.d("Time", timeAndDate[1]);
                Log.d("Date", timeAndDate[0]);

                urlRequest = UrlRequest.getObject();
                urlRequest.setContext(cashOnDelivery.this);
                urlRequest.setUrl("http://192.168.0.107:8001/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_id);
                Log.d("getDataURL: ", "http://192.168.0.107:8001/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_id);
                urlRequest.getResponse(new ServerCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("SendOrder", response);

                        try {

                            jArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject();
                            for (int i = 0; i < jArray.length(); i++) {
                                Log.d("JarrayLength", jArray.length() + "");
                                json_data = jArray.getJSONObject(i);

                                jsonObject.put("jsonObject", json_data);

                            }
                            JSONObject jsonObjectForTimeAndDate = new JSONObject();
                            jsonObjectForTimeAndDate.put("Time", timeAndDate[1]);
                            jsonObjectForTimeAndDate.put("Date", timeAndDate[0]);

                            jsonObject.put("TimeAndDate", jsonObjectForTimeAndDate);

                            Log.d("object", jsonObject + "");
                            RequestQueue requestQueue = Volley.newRequestQueue(cashOnDelivery.this);
                            Log.d("URLorder", "http://192.168.0.107:8001/routes/server/app/myOrder.rfa.php");
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.POST, "http://192.168.0.107:8001/routes/server/app/myOrder.rfa.php", jsonObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {  //Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
                                                Log.d("ResponseOrder", response.getString("response"));
                                              /*  if (response.getString("response").equals("OK"))
                                                {
                                                    Intent intentGoToCart = new Intent(getContext(), GoToCart.class);
                                                    intentGoToCart.putExtra("AUTH_ID", auth_Id);
                                                    startActivity(intentGoToCart);
                                                 }*/

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    VolleyLog.d("Error: ", error.getMessage());
                                }
                            });
                            requestQueue.add(jsonObjReq);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                });
            }
        });
    }
}
