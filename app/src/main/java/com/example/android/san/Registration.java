package com.example.android.san;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Registration extends AppCompatActivity {

    @InjectView(R.id.edtName)
    EditText edtName;
    @InjectView(R.id.edtAddress)
    EditText edtAddress;
    @InjectView(R.id.edtPhone)
    EditText edtPhone;
    @InjectView(R.id.btnRegister)
    Button btnRegister;
    @InjectView(R.id.edtPassword)
    EditText edtPass;
    String name, address, phone, password;
    UrlRequest urlRequest;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btnRegister})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnRegister:
                name = edtName.getText().toString().trim();

                if (name.length() == 0) {
                    edtName.setError("Please enter name");
                    flag = 1;
                }
                password = edtPass.getText().toString().trim();
                if (password.length() == 0) {
                    edtPass.setError("Please enter password");
                    flag = 1;
                }

                phone = edtPhone.getText().toString().trim();
                if (phone.length() < 10 || phone.length() == 0) {
                    edtPhone.setError("Please enter phone number");
                    flag = 1;
                }
                address = edtAddress.getText().toString();
                if (address.length() == 0) {
                    edtAddress.setError("Please enter address");
                    flag = 1;
                }
                if (flag == 0) {
                    urlRequest = UrlRequest.getObject();
                    urlRequest.setContext(getApplicationContext());
                    urlRequest.setUrl("http://192.168.0.22:8001/routes/server/app/userData.rfa.php?name=" + name + "&password=" + password + "&phone=" + phone + "&address=" + address);
                    urlRequest.getResponse(new ServerCallback() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("Response*", response);
                        }
                    });
                }
                break;
        }
    }
}
