package com.example.android.san.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.san.R;
import com.example.android.san.TabActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;



public class FlexibleFragment extends Fragment {

    @InjectView(R.id.txtTiffinType)
    TextView txtTiffinType;
    @InjectView(R.id.txtTiffinType1)
    TextView txtTiffinType1;
  /*  @InjectView(R.id.txtPrice1)
    TextView txtPrice1;*/
    String type, tiffinType,price;
    @InjectView(R.id.cardBasic)
    CardView cardBasic;
    @InjectView(R.id.cardHeavy)
    CardView cardHeavy;
    Intent intent;
Context context;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.activity_flexible_fragment, container, false);
        ButterKnife.inject(this,view);
       /* Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getAssets(),"fonts/Charrington.ttf", true);
*/
         intent=new Intent(getActivity(), TabActivity.class);
         type="flexible";
         intent.putExtra("Type",type);
         price="120";
         intent=intent.putExtra("Price",price);
        return view;
    }

    @OnClick({R.id.cardBasic, R.id.cardHeavy})
    public void onClick(View view)
    {

        switch (view.getId()) {
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