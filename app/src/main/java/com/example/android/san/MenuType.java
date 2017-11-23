package com.example.android.san;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MenuType extends AppCompatActivity {

    @InjectView(R.id.txtFlexible)
    TextView txtFlexible;
    @InjectView(R.id.txtSemiFlexible)
    TextView txtSemiFlexible;
    @InjectView(R.id.txtFixed)
    TextView txtFixed;
    @InjectView(R.id.txtBreakfast)
    TextView txtBreakfast;
    @InjectView(R.id.txtMenuType)
    TextView txtMenuType;
    @InjectView(R.id.imageMenu)
    ImageView imageMenu;
    @InjectView(R.id.txtPrice)
    TextView txtPrice;
    @InjectView(R.id.txtTiffinType)
    TextView txtTiffinType;
    @InjectView(R.id.txtTiffinType1)
    TextView txtTiffinType1;
    @InjectView(R.id.txtPrice1)
    TextView txtPrice1;
    String type, tiffinType;
    @InjectView(R.id.cardBasic)
    CardView cardBasic;
    @InjectView(R.id.cardHeavy)
    CardView cardHeavy;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_type);
        ButterKnife.inject(MenuType.this);
        intent = new Intent(MenuType.this, TabActivity.class);
    }

    @OnClick({R.id.txtFlexible, R.id.txtSemiFlexible, R.id.txtFixed, R.id.txtBreakfast, R.id.cardBasic, R.id.cardHeavy})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txtFlexible:
                type=txtFlexible.getText().toString();
                intent.putExtra("Type", type);
                txtMenuType.setText(type);
                if (type.equals("Flexible")) {
                    txtFlexible.setBackgroundColor(Color.RED);
                    txtSemiFlexible.setBackgroundColor(Color.WHITE);
                    txtFixed.setBackgroundColor(Color.WHITE);
                    txtBreakfast.setBackgroundColor(Color.WHITE);
                } else {
                    txtFlexible.setBackgroundColor(Color.WHITE);
                }
                txtPrice.setText("Rs.120");
                txtPrice1.setText("Rs.150");
                break;
            case R.id.txtSemiFlexible:
                type=txtSemiFlexible.getText().toString();
                intent.putExtra("Type", type);
                if (type.equals("Semi Flexible")) {
                    txtSemiFlexible.setBackgroundColor(Color.RED);
                    txtFlexible.setBackgroundColor(Color.WHITE);
                    txtFixed.setBackgroundColor(Color.WHITE);
                    txtBreakfast.setBackgroundColor(Color.WHITE);
                } else {
                    txtSemiFlexible.setBackgroundColor(Color.WHITE);
                }
                txtMenuType.setText(type);
                txtPrice.setText("Rs.100");
                txtPrice1.setText("Rs.110");
                break;
            case R.id.txtFixed:
                type=txtFixed.getText().toString();
                intent.putExtra("Type", type);
                if (type.equals("Fixed")) {
                    txtFixed.setBackgroundColor(Color.RED);
                    txtSemiFlexible.setBackgroundColor(Color.WHITE);
                    txtFlexible.setBackgroundColor(Color.WHITE);
                    txtBreakfast.setBackgroundColor(Color.WHITE);
                } else {

                    txtFixed.setBackgroundColor(Color.WHITE);
                }
                txtMenuType.setText(type);
                txtPrice.setText("Rs.60");
                txtPrice1.setText("Rs.90");
                break;
            case R.id.txtBreakfast:
                type = txtBreakfast.getText().toString();
                intent.putExtra("Type", type);
                if (type.equals("Breakfast")) {
                    txtBreakfast.setBackgroundColor(Color.RED);
                    txtFlexible.setBackgroundColor(Color.WHITE);
                    txtFixed.setBackgroundColor(Color.WHITE);
                    txtSemiFlexible.setBackgroundColor(Color.WHITE);
                } else {
                    txtBreakfast.setBackgroundColor(Color.WHITE);
                }
                txtMenuType.setText(type);
                txtPrice.setText("Rs.100");
                txtPrice1.setText("Rs.110");
                break;
            case R.id.cardBasic:
                tiffinType = txtTiffinType.getText().toString();
                intent.putExtra("TiffinType", tiffinType);
                startActivity(intent);
                break;
            case R.id.cardHeavy:
                tiffinType = txtTiffinType1.getText().toString();
                intent.putExtra("TiffinType", tiffinType);
                startActivity(intent);
                break;
        }
    }
}
