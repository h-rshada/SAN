package com.example.android.san;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActvity extends AppCompatActivity {

    // View view;
    @InjectView(R.id.imageflexible)
    ImageView imageflexible;
    @InjectView(R.id.imagesemiflex)
    ImageView imagesemiflex;
    @InjectView(R.id.imagefixed)
    ImageView imagefixed;
    @InjectView(R.id.imagebrakfast)
    ImageView imagebrakfast;
    @InjectView(R.id.txtFlexible)
    TextView txtFlexible;
    @InjectView(R.id.txtSemiFlexible)
    TextView txtSemiFlexible;
    @InjectView(R.id.txtFixed)
    TextView txtFixed;
    @InjectView(R.id.txtBreakfast)
    TextView txtBreakfast;

    @InjectView(R.id.more)
    RippleView rippleView;

    Intent intent;
    String type,tiffintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_actvity);
        ButterKnife.inject(OrderActvity.this);
        intent = new Intent(OrderActvity.this, MenuType.class);
    }

    @OnClick({R.id.imageflexible, R.id.imagesemiflex, R.id.imagefixed, R.id.imagebrakfast})
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.imageflexible:
                type = txtFlexible.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);

                break;
            case R.id.imagesemiflex:
                type = txtSemiFlexible.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            case R.id.imagefixed:

                type = txtFixed.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);
                break;

            case R.id.imagebrakfast:
                type = txtBreakfast.getText().toString();
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
        }
        rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Sample", "Click Rect !");
            }
        });
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
            }

        });
    }


}


