package com.example.android.san;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Auth0 auth0;
    String name, address, phone, email, email2;
    UrlRequest urlRequest;
    String id;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int flag = 0;
    private AuthenticationAPIClient authenticationClient;
    private UserProfile userProfile;
    private Button editProfileButton;
    private Button cancelEditionButton;
    /*  private TextView userNameTextView;
      private TextView userEmailTextView;
      private TextView userAddressTextView;
      private TextView userPhoneTextView;*/
    private EditText updateAddressEditText;
    private EditText updatePhoneEditText;
    private EditText updateNameEditText;
    private EditText updateEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        authenticationClient = new AuthenticationAPIClient(auth0);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        editor.commit();
       /* Button refreshTokenButton = findViewById(R.id.refreshTokenButton);
        Button logoutButton = findViewById(R.id.logout);

        refreshTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renewAuthentication();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });*/

        authenticationClient.userInfo(CredentialManager.getCredentials(this).getAccessToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(final UserProfile profile) {
                        userProfile = profile;
                        runOnUiThread(new Runnable() {
                            public void run() {

                                refreshScreenInformation();
                                // checkData(); // it gets auth_id and checks if it already exists it goes to GoToCart,else it stays here.
                            }
                        });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "User Profile Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        editProfileButton = findViewById(R.id.editButton);
        //cancelEditionButton = (Button) findViewById(R.id.cancelEditionButton);
      /*  userNameTextView = findViewById(R.id.userNameTitle);
        userEmailTextView = findViewById(R.id.userEmailTitle);
        userAddressTextView = findViewById(R.id.userAddressTitle);
        userPhoneTextView = findViewById(R.id.userPhoneTitle);*/
        updateNameEditText = findViewById(R.id.updateNameEdittext);
        updateAddressEditText = findViewById(R.id.updateAddressEdittext);
        updatePhoneEditText = findViewById(R.id.updatePhoneEdittext);
        updateEmailEditText = findViewById(R.id.updateEmailEdittext);
        Button loginAgainButton = findViewById(R.id.login_again);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfile();
                //putUserInfo();
            }
        });

        loginAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAgain();
            }
        });

    }

    /* private void renewAuthentication() {
         String refreshToken = CredentialManager.getCredentials(this).getRefreshToken();
         authenticationClient.renewAuth(refreshToken).start(new BaseCallback<Credentials, AuthenticationException>() {
             @Override
             public void onSuccess(final Credentials payload) {
                 runOnUiThread(new Runnable() {
                     public void run() {
                         Toast.makeText(MainActivity.this, "New access_token: " + payload.getAccessToken(), Toast.LENGTH_SHORT).show();
                     }
                 });
             }

             @Override
             public void onFailure(AuthenticationException error) {
                 runOnUiThread(new Runnable() {
                     public void run() {
                         Toast.makeText(MainActivity.this, "Failed to get the new access_token", Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         });
     }

     private void logout() {
         CredentialManager.deleteCredentials(this);
         startActivity(new Intent(this, LoginActivity.class));
         finish();
     }
 */
    @SuppressLint("StringFormatInvalid")
    private void refreshScreenInformation() {
        //checkData();
      /*  userEmailTextView.setText(String.format(getString(R.string.useremail), userProfile.getEmail()));*/
        Toast.makeText(MainActivity.this, "Id" + userProfile.getId(), Toast.LENGTH_SHORT).show();
        Log.d("Id", userProfile.getId());
        email = userProfile.getEmail();
        Log.d(email, "refreshScreenInformation:");

/*
        ImageView userPicture = (ImageView) findViewById(R.id.userPicture);
        if (userProfile.getPictureURL() != null) {
            Picasso.with(this)
                    .load(userProfile.getPictureURL())
                    .into(userPicture);
        }
*/
        String name1 = (String) userProfile.getUserMetadata().get("name");
        if (name1 != null && !name1.isEmpty()) {
//            userNameTextView.setVisibility(View.VISIBLE);
//            userNameTextView.setText(String.format(getString(R.string.username), name1));
        } else {
            flag = 1;
        }
       /* String email1 = (String) userProfile.getUserMetadata().get("email");
        if (email1 != null && !email1.isEmpty()) {
            userNameTextView.setVisibility(View.VISIBLE);
            userNameTextView.setText(String.format(getString(R.string.username), name1));
        }
        else
        {
            flag=1;
        }*/
        String address1 = (String) userProfile.getUserMetadata().get("address");

        if (address1 != null && !address1.isEmpty()) {
//            userAddressTextView.setVisibility(View.VISIBLE);
//            userAddressTextView.setText(String.format(getString(R.string.userAddress), address1));
        } else {
            flag = 1;
        }

        String phone1 = (String) userProfile.getUserMetadata().get("phone");
        if (phone1 != null && !phone1.isEmpty()) {
//            userPhoneTextView.setVisibility(View.VISIBLE);
//            userPhoneTextView.setText(String.format(getString(R.string.userPhone), phone1));
        } else {
            flag = 1;
        }
        flag = 0;

    }

    private void editProfile() {
        if (userProfile == null) {
            return;
        }
      /*  if (cancelEditionButton.getVisibility() == View.GONE) {
            editModeOn(true);
        } */
        else {

            name = updateNameEditText.getText().toString();
            if (name != null && !name.isEmpty()) {
                updateInformation();
            } else {
                flag = 1;
                updateNameEditText.setError("Please enter name");
            }

            email2 = updateEmailEditText.getText().toString();
            if (email2 != null && !email2.isEmpty()) {
                updateInformation();
            } else {
                flag = 1;
                updateNameEditText.setError("Please enter email");
            }
            address = updateAddressEditText.getText().toString();
            if (address != null && !address.isEmpty()) {
                updateInformation();
            } else {
                flag = 1;
                updateAddressEditText.setError("Please enter address");
            }

            phone = updatePhoneEditText.getText().toString();
            if (phone.length() < 10 || phone.length() == 0) {
                flag = 1;
                updatePhoneEditText.setError("Please enter phone number");

            } else {
                updateInformation();
            }
            if (flag == 0) {
                putUserInfo();
            }

            // editModeOn(false);
        }
    }

    private void updateInformation() {
        Map<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("address", address);
        userMetadata.put("phone", phone);
        userMetadata.put("name", name);
        userMetadata.put("email", email2);
        final UsersAPIClient usersClient = new UsersAPIClient(auth0, CredentialManager.getCredentials(MainActivity.this).getIdToken());
        usersClient.updateMetadata(userProfile.getId(), userMetadata)
                .start(new BaseCallback<UserProfile, ManagementException>() {
                    @Override
                    public void onSuccess(final UserProfile profile) {
                        userProfile = profile;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                refreshScreenInformation();
                            }
                        });
                    }

                    @Override
                    public void onFailure(ManagementException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "Profile Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    public void putUserInfo() {

        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getApplicationContext());
        Log.d("Url", "http://sansmealbox.com/admin/routes/server/app/userData.rfa.php?name=" + name + "&auth_id=" + userProfile.getId() + "&phone=" + phone + "&address=" + address + "&email=" + email2);
        urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/userData.rfa.php?name=" + name + "&auth_id=" + userProfile.getId() + "&phone=" + phone + "&address=" + address + "&email=" + email2);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("Response*", response);
                if (response.contains("OK")) {
                    Intent intent = new Intent(MainActivity.this, MenuTypeTab.class);
                    intent.putExtra("PARENT_ACTIVITY_NAME", "MainActivity");
                    finish();
                    startActivity(intent);
                } else if (response.contains("UPDATED")) {
                    editor.putString("AUTH_ID", userProfile.getId());
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Profile.class);
                    finish();
                    startActivity(intent);
                }

                editor.putString("NAME", name);
                editor.putString("PHONE", phone);
                editor.putString("ADDRESS", address);
                editor.putString("EMAIL", email2);
                editor.commit();
            }
        });
    }

    /*   public void checkData() {

        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getApplicationContext());
        Log.d("checkData: ","http://sansmealbox.com/admin/routes/server/app/checkUserInfo.rfa.php?auth_id=" + userProfile.getId() );
        urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/checkUserInfo.rfa.php?auth_id=" + userProfile.getId());
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("Response*", response);
                if (response.contains("EXISTS"))
                {
                    Intent intent = new Intent(MainActivity.this, GoToCart.class);
                    startActivity(intent);

                }
            }
        });
    }*/
    private void loginAgain() {
        CredentialManager.deleteCredentials(MainActivity.this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
