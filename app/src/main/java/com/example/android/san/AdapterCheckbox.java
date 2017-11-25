package com.example.android.san;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 23/11/17.
 */

public class AdapterCheckbox extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    List<DataSubji> data = Collections.emptyList();

    MyHolder myHolder;
    private Context context;
    private LayoutInflater inflater;
    int count=0;

    // create constructor to innitilize context and data sent frm MainActivity
    public AdapterCheckbox(Context context, List<DataSubji> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

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
        DataSubji dataSubji = data.get(position);
        myHolder.checkBox.setText(dataSubji.subji);
        if(count<2) {

            myHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "clicked"+count, Toast.LENGTH_SHORT).show();
                    count++;
                }
            });
        }
        else
        {
            Toast.makeText(context, "can't clicked", Toast.LENGTH_SHORT).show();
        }

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
            /*if(count<2) {
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else
            {
                Toast.makeText(context, "can't clicked", Toast.LENGTH_SHORT).show();
            }*/
        }
    }
}


