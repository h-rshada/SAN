package com.example.android.san;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrderDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.txtMenu)
    TextView txtMenu;
    @InjectView(R.id.txtMenu1)
    TextView txtMenu1;
    @InjectView(R.id.txtBread)
    TextView txtBread;
    @InjectView(R.id.txtRice)
    TextView txtRice;
    @InjectView(R.id.txtDal)
    TextView txtDal;
    Set menuset;
    SharedPreferences sp;
    String bread, rice, dal, amtoil, oiltype, heat, salt, menu, menu1, type, tiffintype, tt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.inject(this);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        menuset = new HashSet<String>();
        type = sp.getString("TYPE", null);
        tiffintype = sp.getString("TIFFIN", null);

        Log.d("type", type);
        Log.d("Tiffin", tiffintype);
        if (tiffintype.equals("Basic")) {
            menu = sp.getString("BASIC", null);
            txtMenu.setText(menu);
            Log.d("Menu", menu);
        } else if (tiffintype.equals("Heavy"))
        {

            if (type.equals("semiFlexible")) {
                txtMenu.setText("1." + sp.getString("SEMISTR1", null));
                txtMenu1.setVisibility(View.VISIBLE);
                txtMenu1.setText("2." + sp.getString("SEMISTR2", null));
            } else {
                menuset = sp.getStringSet("HEAVY", null);
                List listOfNames = new ArrayList(menuset);
                Log.d("----------->", listOfNames.size() + "");
                if (listOfNames.size() == 0) {
                    Toast.makeText(this, "Select atleast 2 sabjis", Toast.LENGTH_SHORT).show();
                }
                menu = listOfNames.get(0).toString();
                menu1 = listOfNames.get(1).toString();
                txtMenu.setText("1." + menu);
                txtMenu1.setVisibility(View.VISIBLE);
                txtMenu1.setText("2." + menu1);
                Log.d("Adapterlist***", listOfNames.get(0) + "");
                Log.d("Menu**", menu);
            }
        }
        bread = getIntent().getStringExtra("Bread");
        rice = getIntent().getStringExtra("Rice");
        dal = getIntent().getStringExtra("Dal");
        heat = getIntent().getStringExtra("Heat");
        salt = getIntent().getStringExtra("Salt");
        amtoil = getIntent().getStringExtra("AmtOil");
        oiltype = getIntent().getStringExtra("OilType");

        Log.d("Bread", bread);
        Log.d("Rice", rice);
        Log.d("Dal", dal);
        Log.d("Heat", heat);
        Log.d("Salt", salt);
        Log.d("AmtOil", amtoil);
        Log.d("OilType", oiltype);
        txtBread.setText(bread);
        txtRice.setText(rice);
        txtDal.setText(dal);
    }
}
