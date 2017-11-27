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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 23/11/17.
 */

public class AdapterCheckbox extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public int SELECTION = 0;
    List<DataSubji> data = Collections.emptyList();
    MyHolder myHolder;
    UrlRequest urlRequest;
    String selectedStr, type;
    ArrayList<String> list;
    DataSubji dataSubji, dataSubji1;
    SharedPreferences sp;
    Set<String> listData;
    private Context context;
    private LayoutInflater inflater;

    // create constructor to innitilize context and data sent frm MainActivity
    public AdapterCheckbox(Context context, List<DataSubji> data) {
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
        View view = inflater.inflate(R.layout.container_checkbox, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        final MyHolder myHolder = (MyHolder) holder;
        final int pos = position;
        final DataSubji dataSubji = data.get(position);
        myHolder.checkBox.setText(dataSubji.subji);
        listData = new HashSet<String>();
        sp = context.getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        type = sp.getString("TYPE", null);
        selectedStr = sp.getString("SINGLE", null);
        listData = sp.getStringSet("LIST", null);
        List listOfNames = new ArrayList(listData);
        Log.d("AdapterDabba***", type);
        Log.d("Adapterstr***", selectedStr);
        Log.d("Adapterlist***", listOfNames.get(0) + "");

        /*myHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SELECTION<2) 
                {
                    myHolder.checkBox.setChecked(true);
                    SELECTION++;
                }
                else
                {
                    myHolder.checkBox.setChecked(false);

                    Toast.makeText(context,"can't click"+SELECTION,Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        myHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (type.equals("fixed")) {
                        if (SELECTION < 2) {
                            compoundButton.setChecked(true);
                            SELECTION++;
                            Toast.makeText(context, "changed" + SELECTION + b + "" + dataSubji.selectedSubji, Toast.LENGTH_SHORT).show();
                        } else {

                            // Toast.makeText(context, "You can select only 2 subji", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                            alertDialog.setMessage("For subji you have to pay extra charges. ");

                            alertDialog.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            compoundButton.setChecked(true);
                                            dialog.cancel();
                                        /*finish();
                                        onBackPressed();*/

                                        }
                                    });
                            alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    compoundButton.setChecked(false);
                                }
                            });

                            alertDialog.show();
                        }
                    }
                } else {
                    if (SELECTION > 0) {
                        compoundButton.setChecked(false);
                        SELECTION--;
                        Toast.makeText(context, "changed" + SELECTION + b + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public MyHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox_menu);
        }
    }

}


