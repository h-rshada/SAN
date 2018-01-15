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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class payment extends AppCompatActivity {
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.btn_placeOrder)
    Button btnPlaceOrder;
    String payment_mode = null;
    String parentActivityName,orderObject;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean login;
    String auth_id;
    @InjectView(R.id.img_back)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        login = sp.getBoolean("LOGIN", false);
        auth_id = sp.getString("AUTH_ID", "");
        intent = getIntent();
        parentActivityName = intent.getStringExtra("PARENT_ACTIVITY_NAME");
        orderObject = intent.getStringExtra("OBJECT");
        Log.d("ParentActivity", parentActivityName);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    payment_mode = rb.getText().toString();

                }

            }
        });
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment_mode == null) {
                    Toasty.success(payment.this, "Please select payment mode", Toast.LENGTH_LONG, true).show();

                } else {
                    if (payment_mode.equals("Cash on delivery")) {
                        Intent intent = new Intent(payment.this, cashOnDelivery.class);
                        if (parentActivityName.equals("GoToCart")) {
                            intent.putExtra("PARENT_ACTIVITY_NAME", parentActivityName);
                            editor.putString("AUTH_ID", auth_id);
                            editor.putBoolean("LOGIN", login);
                            editor.commit();
                            startActivity(intent);
                            finish();
                        }else {
                            intent.putExtra("PARENT_ACTIVITY_NAME", parentActivityName);
                            intent.putExtra("OBJECT",orderObject);
                            startActivity(intent);
                            finish();
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
        if (parentActivityName.equals("GoToCart")) {
            editor = sp.edit();
            editor.putBoolean("LOGIN", true);
            editor.putString("AUTH_ID", auth_id);
            editor.commit();
            Intent intent = new Intent(payment.this, GoToCart.class);
            startActivity(intent);
            payment.this.finish();
        } else {
            editor = sp.edit();
            editor.putBoolean("LOGIN", true);
            editor.putString("AUTH_ID", auth_id);
            editor.commit();
            Intent intent = new Intent(payment.this, HomeActivity.class);
            startActivity(intent);
            payment.this.finish();
        }
    }
}
