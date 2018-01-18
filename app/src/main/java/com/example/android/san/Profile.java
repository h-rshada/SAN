package com.example.android.san;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Profile extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Intent intent;
    UrlRequest urlRequest;
    String id, name, address, email, phone;
    AlertDialog alertDialog;
    @InjectView(R.id.txtName)
    TextView txtName;
    @InjectView(R.id.txtAddress)
    TextView txtAddress;
    @InjectView(R.id.txtPhone)
    TextView txtPhone;
    @InjectView(R.id.txtEmail)
    TextView txtEmail;
    @InjectView(R.id.txtLogin)
    TextView txtLogout;
    @InjectView(R.id.imgEdit)
    ImageView imgEdit;
    boolean login;
    AlertDialog.Builder alert;
    CardView cardAddress, cardPhone, cardEmail;
    @InjectView(R.id.img_back)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        //  Log.d("Login@@@", login + "");
        editor = sp.edit();
        cardPhone = findViewById(R.id.cardPhone);
        cardAddress = findViewById(R.id.cardAddress);
        cardEmail = findViewById(R.id.cardEmail);
        if (!sp.getBoolean("LOGIN", false)) {
            intent = new Intent(Profile.this, LoginActivity.class);
            intent.putExtra("PARENT_ACTIVITY_NAME", "UserProfile");
            finish();
            startActivity(intent);
        } else {

            getData();
        }


    }

    @OnClick({R.id.txtLogin, R.id.imgEdit, R.id.img_back})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txtLogin:


                alert = new AlertDialog.Builder(Profile.this);

                alert.setMessage("Are you sure you want to logout?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor = sp.edit();
                        editor.putBoolean("LOGIN", false);
                        editor.putString("AUTH_ID", null);
                        editor.commit();
                        boolean login = sp.getBoolean("LOGIN", false);
                        Log.d("LOgin***", login + "");
                        CredentialManager.deleteCredentials(Profile.this);
                        intent = new Intent(Profile.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad1 = alert.create();
                ad1.show();

                break;
            case R.id.imgEdit:
                intent = new Intent(Profile.this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }


    public void getData() {

        id = sp.getString("AUTH_ID", "");
        Log.d("Id", id);
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getApplicationContext());
        Log.d("checkData: ", "http://sansmealbox.com/admin/routes/server/app/fetchUserData.rfa.php?auth_id=" + id);
        urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/fetchUserData.rfa.php?auth_id=" + id);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("Response*", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        name = jsonObject.getString("name");
                        address = jsonObject.getString("address");
                        email = jsonObject.getString("email");
                        phone = jsonObject.getString("phone");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!name.equals("null")) {
                    txtName.setText(name);
                }

                if (!address.equals("null")) {
                    txtAddress.setText("Address  : " + address);
                } else {
                    cardAddress.setVisibility(View.GONE);
                }
                if (!email.equals("null")) {
                    txtEmail.setText("Email Id  : " + email);
                } else {
                    cardEmail.setVisibility(View.GONE);
                }
                if (!phone.equals("null")) {
                    txtPhone.setText("Phone  :" + phone);
                } else {
                    cardPhone.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (login) {
            intent = new Intent(Profile.this, HomeActivity.class);
            editor.putBoolean("LOGIN", true);
            editor.putString("AUTH_ID", id);
            editor.commit();
            finish();
            startActivity(intent);
        }
    }
}
