package com.example.android.san;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by android on 11/22/17.
 */

public class AdapterRadioButton extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public int mSelectedItem = -1;
    List<DataSubji> data = Collections.emptyList();
    MyHolder myHolder;
    String selectedStr, type;
    DataSubji dataSubji, dataSubji1;
    SharedPreferences sp;
    UrlRequest urlRequest;
    private Context context;
    private LayoutInflater inflater;


    // create constructor to innitilize context and data sent frm MainActivity
    public AdapterRadioButton(Context context, List<DataSubji> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        myHolder = (MyHolder) holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_radiobutton, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        sp = context.getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        type = sp.getString("TYPE", null);
        Log.d("Dabba***", type);
        final int pos = position;
        dataSubji = data.get(position);
        dataSubji1 = new DataSubji();
        Toast.makeText(context, "Selected str" + selectedStr, Toast.LENGTH_SHORT).show();
        myHolder.radioSubji.setChecked(pos == mSelectedItem);
        myHolder.radioSubji.setText(dataSubji.subji);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {

    }

    class MyHolder extends RecyclerView.ViewHolder {
        RadioButton radioSubji;

        public MyHolder(View itemView) {
            super(itemView);
            radioSubji = itemView.findViewById(R.id.radioSubji);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, data.size());
                }
            };
            itemView.setOnClickListener(clickListener);
            radioSubji.setOnClickListener(clickListener);
        }
    }


}
