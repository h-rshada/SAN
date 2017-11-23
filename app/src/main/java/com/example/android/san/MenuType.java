package com.example.android.san;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @InjectView(R.id.txtMenuType)
    TextView txtMenuType;
    @InjectView(R.id.imageMenu)
    ImageView imageMenu;
    @InjectView(R.id.txtPrice)
    TextView txtPrice;
    @InjectView(R.id.txtPrice1)
    TextView txtPrice1;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_type);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.txtFlexible, R.id.txtSemiFlexible, R.id.txtFixed, R.id.txtBreakfast})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txtFlexible:
                type=txtFlexible.getText().toString();
                txtMenuType.setText(type);
                break;

        }
    }
}
