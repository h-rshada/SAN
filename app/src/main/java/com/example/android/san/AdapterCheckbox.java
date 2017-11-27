package com.example.android.san;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 23/11/17.
 */

public class AdapterCheckbox extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public int SELECTION = 0;
    List<DataSubji> data = Collections.emptyList();
    MyHolder myHolder;
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
        DataSubji dataSubji = data.get(position);
        myHolder.checkBox.setText(dataSubji.subji);
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
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (SELECTION < 2) {
                        compoundButton.setChecked(true);
                        SELECTION++;
                        Toast.makeText(context, "changed" + SELECTION + b + "", Toast.LENGTH_SHORT).show();
                    } else {
                        compoundButton.setChecked(false);
                        Toast.makeText(context, "You can select only 2 subji", Toast.LENGTH_SHORT).show();
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


