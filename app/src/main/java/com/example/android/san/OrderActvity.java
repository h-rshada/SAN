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
    @InjectView(R.id.txtHeavyDabba)TextView txtHeavyDabba;
    @InjectView(R.id.txtBasicDabba)TextView txtBasicDabba;
    @InjectView(R.id.txtHeavyDabba1)TextView txtHeavyDabba1;
    @InjectView(R.id.txtBasicDabba1)TextView txtBasicDabba1;
    @InjectView(R.id.txtHeavyDabba2)TextView txtHeavyDabba2;
    @InjectView(R.id.txtBasicDabba2)TextView txtBasicDabba2;
    @InjectView(R.id.section1)View section1;
    @InjectView(R.id.section2)View section2;
    @InjectView(R.id.section3)View section3;
    Intent intent;
    String type,tiffintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_actvity);
        ButterKnife.inject(OrderActvity.this);
        intent = new Intent(OrderActvity.this, MenuType.class);
    }
    @OnClick({ R.id.header1,R.id.header2,R.id.header3,R.id.txtBasicDabba,R.id.txtHeavyDabba,R.id.txtBasicDabba1,R.id.txtHeavyDabba1,R.id.txtBasicDabba2,R.id.txtHeavyDabba2})
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.header1:

                if (section1.getVisibility() == View.GONE)
                {
                    type=header1.getText().toString();
                    intent.putExtra("Type",type);
                    section1.setVisibility(View.VISIBLE);
                    section2.setVisibility(View.GONE);
                    section3.setVisibility(View.GONE);
                }
                else
                {
                    section1.setVisibility(View.GONE);
                }
                break;
            case R.id.header2:

                if (section2.getVisibility() == View.GONE)
                {
                    type=header2.getText().toString();
                    intent.putExtra("Type",type);
                    section2.setVisibility(View.VISIBLE);
                    section1.setVisibility(View.GONE);
                    section3.setVisibility(View.GONE);
                }
                else
                {
                    section2.setVisibility(View.GONE);

                }
                break;
            case R.id.header3:

                if (section3.getVisibility() == View.GONE)
                {
                    type=header3.getText().toString();
                    intent.putExtra("Type",type);
                    section3.setVisibility(View.VISIBLE);
                    section1.setVisibility(View.GONE);
                    section2.setVisibility(View.GONE);
                }
                else
                {
                    section3.setVisibility(View.GONE);
                }
                break;
            case R.id.txtBasicDabba:
                tiffintype=txtBasicDabba.getText().toString();
                intent.putExtra("TiffinType",tiffintype);
                startActivity(intent);
                break;
            case R.id.txtBasicDabba1:
                tiffintype=txtBasicDabba1.getText().toString();
                intent.putExtra("TiffinType",tiffintype);
                startActivity(intent);
                break;
            case R.id.txtBasicDabba2:
                tiffintype=txtBasicDabba2.getText().toString();
                intent.putExtra("TiffinType",tiffintype);
                startActivity(intent);
                break;
            case R.id.txtHeavyDabba:
                tiffintype=txtHeavyDabba.getText().toString();
                intent.putExtra("TiffinType",tiffintype);
                startActivity(intent);
                break;
            case R.id.txtHeavyDabba1:
                tiffintype=txtHeavyDabba1.getText().toString();
                intent.putExtra("TiffinType",tiffintype);
                startActivity(intent);
                break;
            case R.id.txtHeavyDabba2:
                tiffintype=txtHeavyDabba2.getText().toString();
                intent.putExtra("TiffinType",tiffintype);
                startActivity(intent);
                break;
        }
    }
}
