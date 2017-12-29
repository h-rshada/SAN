package com.example.android.san;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

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
    int pos;
    View view;
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
        ButterKnife.inject(this, view);
        sp = context.getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        final int pos = position;
        final DataCart tiffin_data = data.get(position);
        myHolder.txt_tiffin_plan.setText(tiffin_data.tiffin_plan);
        myHolder.txt_tiffin_type.setText(tiffin_data.tiffin_type);
        myHolder.txt_menu.setText(tiffin_data.menu);
        myHolder.txt_indian_bread.setText(tiffin_data.indian_bread);
        myHolder.txt_rice.setText(tiffin_data.rice);
        myHolder.txt_dal.setText(tiffin_data.dal);
        myHolder.txt_price.setText(tiffin_data.price);
        myHolder.txt_quantity.setText(tiffin_data.quantity);
        myHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(myHolder.txt_quantity.getText() + "");
                quantity++;
                myHolder.txt_quantity.setText(quantity + "");
            }
        });

        myHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(myHolder.txt_quantity.getText() + "");
                if (quantity > 0) {
                    quantity--;
                    myHolder.txt_quantity.setText(quantity + "");
                }
            }
        });
        myHolder.btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setMessage("Are you want to delete tiffin from cart?");
                alertDialog.setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                urlRequest = UrlRequest.getObject();
                                urlRequest.setContext(context);
                                urlRequest.setUrl("http://192.168.0.22:8001/routes/server/app/removeFromCart.rfa.php?id=" + tiffin_data.id);
                                Log.d("getDataURL: ", "http://192.168.0.22:8001/routes/server/app/removeFromCart.rfa.php?id=" + tiffin_data.id);
                                urlRequest.getResponse(new ServerCallback() {
                                    @Override
                                    public void onSuccess(String response) {


                                        Log.d("ResponseRemove", response);
                                        Activity a = (Activity) context;
                                        a.recreate();

                                    }
                                });
                            }
                        });


                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView txt_tiffin_plan, txt_tiffin_type, txt_menu, txt_indian_bread, txt_rice, txt_dal, txt_price, txt_quantity;
        Button btnAdd, btnRemove, btnRemoveFromCart;
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
            btnAdd = itemView.findViewById(R.id.btn_add);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            btnRemoveFromCart = itemView.findViewById(R.id.btn_removeFromCart);

        }
    }
}



