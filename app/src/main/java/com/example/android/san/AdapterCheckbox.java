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

    public int SELECTION = 0, count = 0;
    List<DataSubji> data = Collections.emptyList();
    MyHolder myHolder;
    UrlRequest urlRequest;
    String selectedStr, type, str, str1, str2, dabba;
    ArrayList<String> list;
    DataSubji dataSubji, dataSubji1;
    SharedPreferences sp;
    Set<String> listData;
    ArrayList selectedList;
    Set<String> selectedlistData;
    SharedPreferences.Editor editor;
    String menu, semiStr1, semiStr2;
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
        sp = context.getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        selectedlistData = new HashSet<String>();
        selectedList = new ArrayList<>();
        // editor.commit();
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        final MyHolder myHolder = (MyHolder) holder;
        final int pos = position;
        final DataSubji dataSubji = data.get(position);
        myHolder.checkBox.setText(dataSubji.subji);
        listData = new HashSet<String>();
        type = sp.getString("TYPE", null);
        dabba = sp.getString("DABBA", null);
        Log.d("AdapterDabba***", dabba);
        Log.d("TypeA", type);
        str = myHolder.checkBox.getText().toString();
        Log.d("Checkbox", str);
        if (dabba.equals("fixedHeavy")) {
            listData = sp.getStringSet("LIST", null);
            List listOfNames = new ArrayList(listData);
            //Log.d("Adapterlist***", listOfNames.get(0) + "");
            str1 = listOfNames.get(0).toString();
            // Log.d("str1", str1);
            str2 = listOfNames.get(1).toString();
            // Log.d("str2", str2);
        }
        if (dabba.equals("semiHeavy")) {
            selectedStr = sp.getString("SINGLE", null);
            //Log.d("Adapterstr***", selectedStr);
        }


        if (type.equals("fixed")) {
            myHolder.checkBox.setChecked(false);
            if (str1.equals(str) || str2.equals(str)) {
                myHolder.checkBox.setChecked(true);
                menu = myHolder.checkBox.getText().toString();
                myHolder.checkBox.setClickable(false);
                selectedList.add(str);
                listData.addAll(selectedList);
                count++;
                editor.putStringSet("HEAVY", listData);
                editor.putInt("COUNT", count);
                editor.commit();
                Log.d("onBindViewHolder: ", selectedList + "");
            }
        }
        if (type.equals("semiFlexible")) {
            myHolder.checkBox.setChecked(false);
            if (str.equals(selectedStr)) {
                myHolder.checkBox.setChecked(true);
                semiStr1 = myHolder.checkBox.getText().toString();
                myHolder.checkBox.setClickable(false);
                count++;
                editor.putString("SEMISTR1", semiStr1);
                editor.putInt("COUNT", count);
                editor.commit();
                //addToList(menu);

            }
        }

        myHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (type.equals("flexible")) {

                        if (SELECTION < 2) {
                            compoundButton.setChecked(true);
                            menu = compoundButton.getText().toString();
                            SELECTION++;
                            count++;
                            addToList(menu);

                        } else {
                            if (SELECTION == 2) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setMessage("For subji you have to pay extra charges. ");
                                alertDialog.setPositiveButton(
                                        "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                compoundButton.setChecked(true);
                                                SELECTION++;
                                                count++;
                                                addToList(compoundButton.getText().toString());
                                                dialog.cancel();

                                            }
                                        });
                                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        compoundButton.setChecked(false);
                                        SELECTION++;

                                    }
                                });

                                alertDialog.show();
                            } else {
                                SELECTION++;
                                addToList(compoundButton.getText().toString());
                                count++;
                            }
                        }
                    } else if (type.equals("semiFlexible")) {
                        if (SELECTION < 1) {
                            compoundButton.setChecked(true);
                            semiStr2 = compoundButton.getText().toString();
                            Log.d(SELECTION + "", "SELECTION");
                            SELECTION++;
                            count++;
                            editor.putString("SEMISTR2", semiStr2);
                            editor.putInt("COUNT", count);
                            editor.commit();
                            //addToList(menu);
                        } else {
                            Toast.makeText(context, "You can select one subji only", Toast.LENGTH_SHORT).show();
                            compoundButton.setChecked(false);
                        }
                    } else if (type.equals("fixed")) {
                        compoundButton.setChecked(false);
                        Toast.makeText(context, "You can't select more than 2 subji's", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (SELECTION > 0) {
                        compoundButton.setChecked(false);
                        SELECTION--;
                        count--;
                        str = compoundButton.getText().toString();
                        removeFromList(str);

                    }
                }
            }
        });
    }

    public void addToList(String str) {
        selectedList.add(str);
        selectedlistData.addAll(selectedList);
        editor.putStringSet("HEAVY", selectedlistData);
        editor.putInt("COUNT", count);
        editor.commit();
        Log.d("onBindViewHolder: ", selectedList + "");
    }

    public void removeFromList(String str) {
        selectedList.remove(str);
        selectedlistData.remove(str);
        editor.putStringSet("HEAVY", selectedlistData);
        editor.putInt("COUNT", count);
        editor.commit();
        Log.d("onBindViewHolder: ", selectedlistData + "");
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


