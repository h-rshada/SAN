package com.example.android.san;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yinglan.keyboard.HideUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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
    Intent intent;
    String parentActivityName;
    JSONObject jsonObject;
    int flag=0;
    @InjectView(R.id.img_back)
    ImageView imageView;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery);
        HideUtil.init(cashOnDelivery.this);

        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        auth_id = sp.getString("AUTH_ID", "");
        intent = getIntent();
        parentActivityName = intent.getStringExtra("PARENT_ACTIVITY_NAME");
        Log.d(parentActivityName, "PAN ");
        Log.d("ParentActivity", parentActivityName);
        Log.d("Login^^^^^^^6666", login + "");
        Log.d("Auth_id", auth_id);
        Log.d("I m in cashon delivery", "Cash");
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(cashOnDelivery.this);
        Log.d("URL", "http://sansmealbox.com/admin/routes/server/app/getAddress.rfa.php?AuthId=" + auth_id);
        urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/getAddress.rfa.php?AuthId=" + auth_id);
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
                if (flag == 0) {
                    flag = 1;
                    if(parentActivityName.equals("GoToCart")) {
                        urlRequest = UrlRequest.getObject();
                        urlRequest.setContext(cashOnDelivery.this);
                        urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_id);
                        Log.d("getDataURL: ", "http://sansmealbox.com/admin/routes/server/app/fetchCartItems.rfa.php?auth_id=" + auth_id);
                        urlRequest.getResponse(new ServerCallback() {
                            @Override
                            public void onSuccess(String response) {
                                Log.d("SendOrder", response);

                                try {

                                    jArray = new JSONArray(response);
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("jsonObject", jArray);
                                    Log.d("object", jsonObject + "");
                                    RequestQueue requestQueue = Volley.newRequestQueue(cashOnDelivery.this);
                                    Log.d("URLorder", "http://sansmealbox.com/admin/routes/server/app/myOrder.rfa.php");
                                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                            Request.Method.POST, "http://sansmealbox.com/admin/routes/server/app/myOrder.rfa.php", jsonObject,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {  //Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
                                                        Log.d("ResponseOrder", response.getString("response"));
                                                        Toasty.success(cashOnDelivery.this, "Your order placed successfully..!", Toast.LENGTH_LONG, true).show();

                                                        editor = sp.edit();
                                                        editor.putBoolean("LOGIN", login);
                                                        editor.putString("AUTH_ID", auth_id);
                                                        editor.commit();
                                                        startActivity(new Intent(cashOnDelivery.this, HomeActivity.class));
                                                        finish();

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
                    } else {
                        try {
                            jsonObject = new JSONObject(getIntent().getStringExtra("OBJECT"));
                            Log.d(jsonObject+"", "JJJ");
                            RequestQueue requestQueue = Volley.newRequestQueue(cashOnDelivery.this);
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.POST, "http://sansmealbox.com/admin/routes/server/app/myOrder.rfa.php", jsonObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("ResponseODA", response.toString());
                                            Toasty.success(cashOnDelivery.this, "Your order placed successfully..!", Toast.LENGTH_LONG, true).show();

                                            editor = sp.edit();
                                            editor.putBoolean("LOGIN", login);
                                            editor.putString("AUTH_ID", auth_id);
                                            editor.commit();
                                            startActivity(new Intent(cashOnDelivery.this, HomeActivity.class));
                                            finish();

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
                }
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
        editor.putBoolean("LOGIN", true);
        editor.putString("AUTH_ID", auth_id);
        editor.commit();
        intent = new Intent(cashOnDelivery.this, MenuTypeTab.class);
        startActivity(intent);
        cashOnDelivery.this.finish();
    }
}
