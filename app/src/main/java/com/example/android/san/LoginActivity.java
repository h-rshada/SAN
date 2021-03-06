package com.example.android.san;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences sp;
    UrlRequest urlRequest;
    String id = "";
    UserProfile userProfile1;
    Button loginButton;
    Intent intent;
    String parentActivityName;
    LinearLayout linearLayout;
    @InjectView(R.id.img_back)
    ImageView imageView;
    private Auth0 auth0;
    private AuthenticationAPIClient authenticationClient;
    AuthCallback callback = new AuthCallback() {


        @Override
        public void onFailure(@NonNull final Dialog dialog) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }

        @Override
        public void onFailure(AuthenticationException exception) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toasty.error(LoginActivity.this, "login failed..", Toast.LENGTH_SHORT, true).show();

                }
            });
        }
        @Override
        public void onSuccess(@NonNull Credentials credentials) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    linearLayout.setVisibility(View.GONE);
                    //Toast.makeText(LoginActivity.this, "Log In - Success", Toast.LENGTH_SHORT).show();
                    Toasty.success(LoginActivity.this, "login successful..!", Toast.LENGTH_SHORT, true).show();

                }
            });
//            login=true;

            CredentialManager.saveCredentials(LoginActivity.this, credentials);
            authenticationClient = new AuthenticationAPIClient(auth0);
            authenticationClient.userInfo(CredentialManager.getCredentials(getApplicationContext()).getAccessToken())
                    .start(new BaseCallback<UserProfile, AuthenticationException>() {
                        @Override
                        public void onSuccess(final UserProfile userProfile) {
                            userProfile1 = userProfile;
                            id = userProfile1.getId();
                            Log.d("Id", id);
                            editor.putBoolean("LOGIN", true);
                            editor.commit();
                            editor.putString("AUTH_ID", id);
                            editor.commit();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    checkData(id);
                                }
                            });
                        }

                        @Override
                        public void onFailure(AuthenticationException error) {
                            runOnUiThread(new Runnable() {

                                public void run() {

                                }
                            });
                            CredentialManager.deleteCredentials(LoginActivity.this);
                        }
                    });

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        editor.clear();
        editor.commit();
      /*  id=sp.getString("ID",null);
        Log.d("Id",id);*/
        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        linearLayout = findViewById(R.id.linearLogin);
        loginButton = findViewById(R.id.loginButton);
        intent = getIntent();
        parentActivityName = intent.getStringExtra("PARENT_ACTIVITY_NAME");
        Log.d("ParentActivity", parentActivityName);

    /*    authenticationClient = new AuthenticationAPIClient(auth0);
        authenticationClient.userInfo(CredentialManager.getCredentials(getApplicationContext()).getAccessToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(final UserProfile userProfile) {
                        userProfile1 = userProfile;
                        id = userProfile1.getId();
                        editor.putString("AUTH_ID", id);
                        editor.putBoolean("LOGIN", true);
                        editor.commit();
                        Log.d("Id1", id);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                checkData(id);

                                Toast.makeText(LoginActivity.this, "Automatic Login Success", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {

                            public void run() {

                                Toast.makeText(LoginActivity.this, "Session Expired, please Log In", Toast.LENGTH_SHORT).show();
                            }
                        });
                        CredentialManager.deleteCredentials(LoginActivity.this);
                    }
                });*/


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        String accessToken = CredentialManager.getCredentials(this).getAccessToken();
        if (accessToken == null) {
            return;
        }

        //If the token exists, try to fetch the associated user info
        loginButton.setEnabled(true);
    }

    private void doLogin() {
        WebAuthProvider.init(auth0)
                .withScheme("demo")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .withScope("openid offline_access")
                .start(this, callback);
    }

    public void checkData(final String id) {

        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getApplicationContext());
        Log.d("checkData: ", "http://sansmealbox.com/admin/routes/server/app/checkUserInfo.rfa.php?auth_id=" + id);
        urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/checkUserInfo.rfa.php?auth_id=" + id);
        try {
            urlRequest.getResponse(new ServerCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.d("Response*", response);
                    if (response.contains("EXISTS")) {

                        if (parentActivityName.equals("GoToCart")) {
                            intent = new Intent(LoginActivity.this, GoToCart.class);
                            editor.putString("AUTH_ID", id);
                            editor.commit();
                            startActivity(intent);
                            finish();
                        } else {
                            intent = new Intent(LoginActivity.this, MenuTypeTab.class);
                            editor.putString("AUTH_ID", id);
                            editor.commit();
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
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
        super.onBackPressed();
        if (parentActivityName.equals("UserProfile")) {
            intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
