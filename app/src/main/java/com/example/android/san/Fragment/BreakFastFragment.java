package com.example.android.san.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.san.R;
import com.example.android.san.TabActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;



public class BreakFastFragment extends Fragment {
    @InjectView(R.id.txtTiffinType)
    TextView txtTiffinType;
    @InjectView(R.id.txtTiffinType1)
    TextView txtTiffinType1;
    @InjectView(R.id.cardBasic)
    CardView cardBasic;
    @InjectView(R.id.cardHeavy)
    CardView cardHeavy;
    Intent intent;

    String type,tiffinType,price;
    View view;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view= inflater.inflate(R.layout.activity_break_fast_fragment, container, false);
         ButterKnife.inject(this,view);

        intent=new Intent(getActivity(), TabActivity.class);
        type="breakfast";
        intent.putExtra("Type",type);
        price="100";
        intent=intent.putExtra("Price",price);
        cardBasic.setBackgroundResource(R.drawable.heavy100);
        cardHeavy.setBackgroundResource(R.drawable.darkback12);

//        txtTiffinType.setPaintFlags(txtTiffinType.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
//        txtTiffinType1.setPaintFlags(txtTiffinType1.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        return  view;
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
