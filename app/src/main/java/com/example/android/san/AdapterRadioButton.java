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
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by android on 11/22/17.
 */

public class AdapterRadioButton extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public int mSelectedItem = -1;
    List<DataSubji> data = Collections.emptyList();
    MyHolder myHolder;
    String selectedStr, type, str, string = null;
    DataSubji dataSubji, dataSubji1;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
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

        final int pos = position;
        sp = context.getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        type = sp.getString("TYPE", null);

        dataSubji = data.get(position);
        myHolder.radioSubji.setChecked(pos == mSelectedItem);
        myHolder.radioSubji.setText(dataSubji.subji);
        str = myHolder.radioSubji.getText().toString();
        // Log.d("RadioSubji", string);

        Log.d("str", str);
        if (type.equals("fixed")) {
            selectedStr = sp.getString("SINGLE", "");
            Log.d("Dabba***", type);
            Log.d("Selected***", selectedStr);
            myHolder.radioSubji.setChecked(false);
            if (selectedStr.equals(str)) {
                myHolder.radioSubji.setChecked(true);
                myHolder.radioSubji.setClickable(false);
                string = str;
                editor.putString("BASIC", string);
                editor.commit();
                Log.d("RadioSubji", string);
            }
        }
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
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            radioSubji = itemView.findViewById(R.id.radioSubji);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, data.size());
                    if (!str.isEmpty()) {
                        string = radioSubji.getText().toString();
                        editor.putString("BASIC", string);
                        editor.commit();
                    } else {
                        editor.putString("BASIC", null);
                    }
                    Log.d("RadioSubji", string);
                }
            };
            itemView.setOnClickListener(clickListener);
            radioSubji.setOnClickListener(clickListener);
        }
    }
}
