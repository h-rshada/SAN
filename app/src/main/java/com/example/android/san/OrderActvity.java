package com.example.android.san;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActvity extends AppCompatActivity {

   // View view;
    @InjectView(R.id.header1)TextView header1;
    @InjectView(R.id.header2)TextView header2;
    @InjectView(R.id.header3)TextView header3;
    @InjectView(R.id.header4)
    TextView header4;

    Intent intent;
    String type,tiffintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_actvity);
        ButterKnife.inject(OrderActvity.this);
        intent = new Intent(OrderActvity.this, MenuType.class);
    }

    @OnClick({R.id.header1, R.id.header2, R.id.header3, R.id.header4})
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.header1:
                type = header1.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);

                break;
            case R.id.header2:
                type = header2.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            case R.id.header3:

                type = header3.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);
                break;

            case R.id.header4:
                type = header4.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
        }
    }
}
