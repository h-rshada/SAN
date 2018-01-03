package com.example.android.san;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class payment extends AppCompatActivity {
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.btn_placeOrder)
    Button btnPlaceOrder;
    String payment_mode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.inject(this);
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
                    Toast.makeText(payment.this, "select payment mode", Toast.LENGTH_SHORT).show();
                } else {
                    if (payment_mode.equals("Cash on delivery")) {
                        Intent intent = new Intent(payment.this, cashOnDelivery.class);
                        startActivity(intent);
                        finish();
                    } else {

                    }
                }

            }
        });
    }
}
