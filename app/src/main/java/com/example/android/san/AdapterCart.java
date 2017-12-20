package com.example.android.san;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 18/12/17.
 */

public class AdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    List<DataCart> data = Collections.emptyList();
    MyHolder myHolder;
    UrlRequest urlRequest;
    ArrayList<String> list;
    SharedPreferences sp;
    Set<String> listData;
    ArrayList selectedList;
    Set<String> selectedlistData;
    SharedPreferences.Editor editor;
    String menu, semiStr1, semiStr2;
    private Context context;
    private LayoutInflater inflater;

    // create constructor to innitilize context and data sent frm MainActivity
    public AdapterCart(Context context, List<DataCart> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        myHolder = (MyHolder) holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_cart, parent, false);
        MyHolder holder = new MyHolder(view);
        sp = context.getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();

        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        final int pos = position;
        final DataCart tiffin_data = data.get(position);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView txt_tiffin_plan, txt_tiffin_type, txt_menu, txt_indian_bread, txt_rice, txt_dal, txt_price, txt_quantity;

        public MyHolder(View itemView) {
            super(itemView);
            txt_tiffin_plan = itemView.findViewById(R.id.txtTiffinType);
            txt_tiffin_type = itemView.findViewById(R.id.txtTiffinPlan);
            txt_menu = itemView.findViewById(R.id.txtMenu);
            txt_indian_bread = itemView.findViewById(R.id.txtBread);
            txt_rice = itemView.findViewById(R.id.txtRice);
            txt_dal = itemView.findViewById(R.id.txtDal);
            txt_price = itemView.findViewById(R.id.txtPrice);
            txt_quantity = itemView.findViewById(R.id.txtQuantity);

        }
    }
}



