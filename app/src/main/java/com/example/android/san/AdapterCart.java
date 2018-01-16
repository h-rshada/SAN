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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

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
    int pos, totalPrice = 0, totalQuantity = 0;
    View view;
    JSONObject orderData;
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
        Log.d("position", position + "");

        myHolder.txt_tiffin_plan.setText(tiffin_data.tiffin_plan);
        myHolder.txt_tiffin_type.setText(tiffin_data.tiffin_type);
        myHolder.txt_menu.setText(tiffin_data.menu);
        myHolder.txt_indian_bread.setText(tiffin_data.indian_bread);
        myHolder.txt_rice.setText(tiffin_data.rice);
        myHolder.txt_dal.setText(tiffin_data.dal);
        myHolder.txt_price.setText((Integer.parseInt(tiffin_data.price) * Integer.parseInt(tiffin_data.quantity)) + "");
        myHolder.txt_quantity.setText(tiffin_data.quantity);
        totalPrice = (Integer.parseInt(tiffin_data.price) * Integer.parseInt(tiffin_data.quantity)) + totalPrice;
        totalQuantity = totalQuantity + Integer.parseInt(tiffin_data.quantity);
        Log.d("totalp", totalPrice + "");
        if (position == (tiffin_data.totalCartItems - 1)) {
            myHolder.linearLayout.setVisibility(View.VISIBLE);
            myHolder.txt_totalQuantity.setText(totalQuantity + "");
            myHolder.txt_totalPrice.setText(totalPrice + "");
            Log.d("Visible", "visible");
            Log.d("totalpl", totalPrice + "");

        }
        myHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(myHolder.txt_quantity.getText() + "");
                quantity++;
                myHolder.txt_quantity.setText(quantity + "");
                updateQuantity(quantity, tiffin_data.id, "add");
                TastyToast.makeText(context, "Another Tiffin box added..!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

            }
        });

        myHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(myHolder.txt_quantity.getText() + "");
                if (quantity > 1) {
                    quantity--;
                    myHolder.txt_quantity.setText(quantity + "");
                    updateQuantity(quantity, tiffin_data.id, "delete");

                    TastyToast.makeText(context, " Tiffin box Removed..", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);


                }
            }
        });
        myHolder.btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete Tiffin....");
                alertDialog.setIcon(R.drawable.delete111);
                alertDialog.setMessage("Are you sure to delete tiffin from cart?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                urlRequest = UrlRequest.getObject();
                                urlRequest.setContext(context);
                                urlRequest.setUrl("http://sansmealbox.com/admin/routes/server/app/removeFromCart.rfa.php?id=" + tiffin_data.id);
                                Log.d("getDataURL: ", "http://sansmealbox.com/admin/routes/server/app/removeFromCart.rfa.php?id=" + tiffin_data.id);
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

    public void updateQuantity(int quantity, String id, String action) {
        orderData = new JSONObject();
        try {
            orderData.put("Id", id);
            orderData.put("Quantity", quantity + "");
            orderData.put("Action", action);
            Log.d("quantityO", orderData + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Log.d("URLorder", "http://sansmealbox.com/admin/routes/server/app/addToCart.rfa.php");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, "http://sansmealbox.com/admin/routes/server/app/addToCart.rfa.php", orderData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ResponseOrderQuantity", response.getString("response"));
                            Activity a = (Activity) context;
                            a.recreate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Error: ", error.getMessage());


            }
        });
        requestQueue.add(jsonObjReq);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_tiffin_plan, txt_tiffin_type, txt_menu, txt_indian_bread, txt_rice, txt_dal, txt_price, txt_quantity, txt_totalPrice, txt_totalQuantity;
        Button btnAdd, btnRemove, btnRemoveFromCart;
        LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layout_linear1);
            txt_tiffin_plan = itemView.findViewById(R.id.txtTiffinType);
            txt_tiffin_type = itemView.findViewById(R.id.txtTiffinPlan);
            txt_menu = itemView.findViewById(R.id.txtMenu4);
            txt_indian_bread = itemView.findViewById(R.id.txtBread);
            txt_rice = itemView.findViewById(R.id.txtRice);
            txt_dal = itemView.findViewById(R.id.txtDal);
            txt_price = itemView.findViewById(R.id.txtPrice);
            txt_quantity = itemView.findViewById(R.id.txtQuantity);
            btnAdd = itemView.findViewById(R.id.btn_add);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            btnRemoveFromCart = itemView.findViewById(R.id.btn_removeFromCart);
            txt_totalPrice = itemView.findViewById(R.id.txt_totalPrice);
            txt_totalQuantity = itemView.findViewById(R.id.txt_totalQuantity);

        }
    }
}



