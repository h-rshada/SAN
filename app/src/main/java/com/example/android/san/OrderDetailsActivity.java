package com.example.android.san;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrderDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.txtMenu)
    TextView txtMenu;
    @InjectView(R.id.txtBread)
    TextView txtBread;
    @InjectView(R.id.txtRice)
    TextView txtRice;
    @InjectView(R.id.txtDal)
    TextView txtDal;
    SharedPreferences sp;
    String bread, rice, dal, amtoil, oiltype, heat, salt, menu, type, tiffintype, tt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        type = sp.getString("TYPE", null);
        tiffintype = sp.getString("TIFFIN", null);

        Log.d("type", type);
        Log.d("Tiffin", tiffintype);
        if (tiffintype.equals("Basic")) {
            menu = sp.getString("BASIC", null);
            Log.d("Menu", menu);
        }
       /* else if(tiffintype.equals("Heavy"))
        {
            menu=sp.getString("HEAVY",null);
            List listOfNames = new ArrayList(menu);
            Log.d("Adapterlist***", listOfNames.get(0) + "");
            Log.d("Menu**",menu);
        }*/
        bread = getIntent().getStringExtra("Bread");
        rice = getIntent().getStringExtra("Rice");
        dal = getIntent().getStringExtra("Dal");
        heat = getIntent().getStringExtra("Heat");
        salt = getIntent().getStringExtra("Salt");
        amtoil = getIntent().getStringExtra("AmtOil");
        oiltype = getIntent().getStringExtra("OilType");
        // menu=getIntent().getStringExtra("Menu");

        Log.d("Bread", bread);
        Log.d("Rice", rice);
        Log.d("Dal", dal);
        Log.d("Heat", heat);
        Log.d("Salt", salt);
        Log.d("AmtOil", amtoil);
        Log.d("OilType", oiltype);

        txtMenu.setText(menu);
        txtBread.setText(bread);
        txtRice.setText(rice);
        txtDal.setText(dal);

    }
}
