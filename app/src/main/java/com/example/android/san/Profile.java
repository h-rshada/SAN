package com.example.android.san;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        Log.d("Login@@@", login + "");
        editor = sp.edit();
        if (!sp.getBoolean("LOGIN", false)) {
            intent = new Intent(Profile.this, LoginActivity.class);
            intent.putExtra("PARENT_ACTIVITY_NAME", "UserProfile");
            startActivity(intent);
        } else {

            getData();
        }


    }

    @OnClick({R.id.txtLogin, R.id.imgEdit})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txtLogin:

                LayoutInflater li = LayoutInflater.from(Profile.this);
                //Creating a view to get the dialog box
                View logoutConfirmDialog = li.inflate(R.layout.dialogue_confirm_logout, null);
                TextView txtYes = logoutConfirmDialog.findViewById(R.id.txtYes);
                TextView txtNo = logoutConfirmDialog.findViewById(R.id.txtNo);
                AlertDialog.Builder alert = new AlertDialog.Builder(Profile.this);
                //Adding our dialog box to the view of alert dialog
                alert.setView(logoutConfirmDialog);
                //Creating an alert dialog
                alertDialog = alert.create();
                alertDialog.show();
                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                txtNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.imgEdit:
                intent = new Intent(Profile.this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }

    public void getData() {

        id = sp.getString("AUTH_ID", "");
        Log.d("Id", id);
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getApplicationContext());
        Log.d("checkData: ", "http://192.168.0.107:8001/routes/server/app/fetchUserData.rfa.php?auth_id=" + id);
        urlRequest.setUrl("http://192.168.0.107:8001/routes/server/app/fetchUserData.rfa.php?auth_id=" + id);
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

                txtName.setText(name);
                txtAddress.setText(address);
                txtEmail.setText(email);
                txtPhone.setText(phone);

            }
        });
    }
}
