package com.example.android.san;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class NonVegFragment extends Fragment {


    @InjectView(R.id.spinner_indianBread)
    Spinner spinner_indianBread;
    @InjectView(R.id.spinner_heat)
    Spinner spinner_heat;
    @InjectView(R.id.spinner_amountOfOil)
    Spinner spinner_amountOfOil;
    @InjectView(R.id.spinner_typeOfOil)
    Spinner spinner_typeOfOil;
    @InjectView(R.id.spinner_salt)
    Spinner spinner_salt;
    @InjectView(R.id.txt_mon)
    TextView day_monday;
    @InjectView(R.id.txt_tue)
    TextView day_tuesday;
    @InjectView(R.id.txt_wed)
    TextView day_wednesday;
    @InjectView(R.id.txt_thu)
    TextView day_thursday;
    @InjectView(R.id.txt_fri)
    TextView day_friday;
    @InjectView(R.id.txt_sat)
    TextView day_saturday;
    @InjectView(R.id.txt_sun)
    TextView day_sunday;
    @InjectView(R.id.recycleCardView)
    CardView cardView;
    @InjectView(R.id.txt_menuNotavailable)
    TextView menuNotAvailable;
    @InjectView(R.id.txt_tiffininfo)
    TextView tiffin_tag;
    @InjectView(R.id.spinner_rice)
    Spinner spinner_rice;
    @InjectView(R.id.spinner_dal)
    Spinner spinner_dal;
    @InjectView(R.id.btnSubmit)
    Button btnSubmit;
    AdapterCheckbox adapterCheckbox;
    AdapterRadioButton adapterRadioButton;
    RecyclerView recyclerView;
    UrlRequest urlRequest;
    DataSubji dataSubji;
    List<DataSubji> arrayList;
    ArrayList arrayList1;
    Set<String> listData;
    View view;
    int count = 0;
    Set menuset;
    String selectedBread, selectedSalt, selectedRice, selectedDal, selectedOil, selectedAmtOil, selectedHeat, spinner_item;
    String item, str[], str1, str2, menu;
    String day, week_day, dabba1, string = null;
    ArrayAdapter adapter_bread, adapter_rice, adapter_dal;
    SharedPreferences sp;
    String type, tiffintype, dabba, t[];
    SharedPreferences.Editor editor;
    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.equals(spinner_indianBread)) {
                selectedBread = spinner_indianBread.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedBread);
            }

            if (adapterView.equals(spinner_dal)) {
                selectedDal = spinner_dal.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedDal);
            }
            if (adapterView.equals(spinner_rice)) {
                selectedRice = spinner_rice.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedRice);
            }
            if (adapterView.equals(spinner_salt)) {
                selectedSalt = spinner_salt.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedSalt);
            }
            if (adapterView.equals(spinner_heat)) {
                selectedHeat = spinner_heat.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedHeat);
            }
            if (adapterView.equals(spinner_amountOfOil)) {
                selectedAmtOil = spinner_amountOfOil.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedAmtOil);
            }
            if (adapterView.equals(spinner_typeOfOil)) {
                selectedOil = spinner_typeOfOil.getSelectedItem().toString();
                adapterView.setSelection(i);
                Log.d("selected item", selectedOil);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            if (adapterView.equals(spinner_indianBread)) {
                selectedBread = spinner_indianBread.getItemAtPosition(0).toString();
            }
            if (adapterView.equals(spinner_rice)) {
                selectedRice = spinner_rice.getItemAtPosition(0).toString();
            }
            if (adapterView.equals(spinner_dal)) {
                selectedDal = spinner_dal.getItemAtPosition(0).toString();
            }
            if (adapterView.equals(spinner_heat)) {
                selectedHeat = spinner_heat.getItemAtPosition(0).toString();
            }
            if (adapterView.equals(spinner_salt)) {
                selectedSalt = spinner_salt.getItemAtPosition(0).toString();
            }
            if (adapterView.equals(spinner_amountOfOil)) {
                selectedAmtOil = spinner_amountOfOil.getItemAtPosition(0).toString();
            }
            if (adapterView.equals(spinner_typeOfOil)) {
                selectedOil = spinner_typeOfOil.getItemAtPosition(0).toString();
            }

        }
    };

    public NonVegFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_veg, container, false);
        ButterKnife.inject(this, view);

        sp = getActivity().getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        editor = sp.edit();
        editor.clear();
        editor.commit();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        day = sdf.format(d);
        if (day.equals("Monday")) {
            day_monday.setSelected(true);
            day_monday.setBackgroundColor(Color.RED);
            week_day = "Monday";
        } else if (day.equals("Tuesday")) {
            day_tuesday.setSelected(true);
            day_tuesday.setBackgroundColor(Color.RED);
            week_day = "Tuesday";
            day_monday.setEnabled(false);
        } else if (day.equals("Wednesday")) {
            day_wednesday.setSelected(true);
            day_wednesday.setBackgroundColor(Color.RED);
            week_day = "Wednesday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);

        } else if (day.equals("Thursday")) {
            day_thursday.setSelected(true);
            day_thursday.setBackgroundColor(Color.RED);
            week_day = "Thursday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
        } else if (day.equals("Friday")) {
            day_friday.setSelected(true);
            day_friday.setBackgroundColor(Color.RED);
            week_day = "Friday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
            day_thursday.setEnabled(false);
        } else if (day.equals("Saturday")) {
            day_saturday.setSelected(true);
            day_saturday.setBackgroundColor(Color.RED);
            week_day = "Saturday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
            day_thursday.setEnabled(false);
            day_friday.setEnabled(false);
        } else if (day.equals("Sunday")) {
            day_sunday.setSelected(true);
            day_sunday.setBackgroundColor(Color.RED);
            week_day = "Sunday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
            day_thursday.setEnabled(false);
            day_friday.setEnabled(false);
            day_saturday.setEnabled(false);
        }

        ArrayAdapter<CharSequence> adapter_heat = ArrayAdapter.createFromResource(getActivity(), R.array.heat, android.R.layout.simple_spinner_item);
        adapter_heat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_heat.setAdapter(adapter_heat);

        ArrayAdapter<CharSequence> adapter_Oiltype = ArrayAdapter.createFromResource(getActivity(), R.array.oil_type, android.R.layout.simple_spinner_item);
        adapter_Oiltype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_typeOfOil.setAdapter(adapter_Oiltype);

        ArrayAdapter<CharSequence> adapter_Oilamount = ArrayAdapter.createFromResource(getActivity(), R.array.oil_amount, android.R.layout.simple_spinner_item);
        adapter_Oilamount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_amountOfOil.setAdapter(adapter_Oilamount);

        ArrayAdapter<CharSequence> adapter_salt = ArrayAdapter.createFromResource(getActivity(), R.array.salt, android.R.layout.simple_spinner_item);
        adapter_salt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salt.setAdapter(adapter_salt);
        type = getArguments().getString("Type").toLowerCase();
        t = getArguments().getString("TiffinType").split(" ");
        tiffintype = t[0];
        Log.d("Type", type);
        Log.d("TiffinType", tiffintype);
        if (type.equals("semi flexible")) {
            str = type.split(" ");
            str1 = str[0];
            str2 = str[1];
            type = "semiFlexible";
            Log.d("Semi type", type);
            dabba = str1 + tiffintype;
            Log.d("Split", str1);
        } else {
            dabba = type + tiffintype;
        }
        spinner_heat.setOnItemSelectedListener(itemSelectedListener);
        spinner_indianBread.setOnItemSelectedListener(itemSelectedListener);
        spinner_rice.setOnItemSelectedListener(itemSelectedListener);
        spinner_dal.setOnItemSelectedListener(itemSelectedListener);
        spinner_amountOfOil.setOnItemSelectedListener(itemSelectedListener);
        spinner_salt.setOnItemSelectedListener(itemSelectedListener);
        spinner_typeOfOil.setOnItemSelectedListener(itemSelectedListener);

        Log.d(dabba, "Dabba ");
        editor.putString("DABBA", dabba);
        editor.putString("TIFFIN", tiffintype);
        editor.putString("TYPE", type);
        //editor.putStringSet("HEAVY",null);
        editor.commit();
        String output = type.substring(0, 1).toUpperCase() + type.substring(1);
        tiffin_tag.setText(output + "/" + tiffintype);

        item = "bread";

        setData("http://192.168.0.22:8001/routes/server/app/getCommonItems.php?item=bread", item);

        item = "rice";

        setData("http://192.168.0.22:8001/routes/server/app/getCommonItems.php?item=rice", item);

        item = "dal";

        setData("http://192.168.0.22:8001/routes/server/app/getCommonItems.php?item=dal", item);

        Log.d("onCreateView: ", arrayList1 + "");
        getData();
        if (dabba.equals("semiHeavy") || dabba.equals("fixedBasic") || dabba.equals("fixedHeavy")) {
            selectedData();
        }
        return view;

    }

    @OnClick({R.id.txt_mon, R.id.txt_tue, R.id.txt_wed, R.id.txt_thu, R.id.txt_fri, R.id.txt_sat, R.id.txt_sun, R.id.btnSubmit})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txt_mon:

                week_day = "Monday";
                getData();
                //day_monday.setBackgroundResource(R.drawable.style_round1);

                break;
            case R.id.txt_tue:
                week_day = "Tuesday";
                getData();

                break;
            case R.id.txt_wed:
                week_day = "Wednesday";
                getData();

                break;
            case R.id.txt_thu:
                week_day = "Thursday";
                //day_thursday.setBackgroundResource(R.drawable.style_round1);
                getData();
                break;
            case R.id.txt_fri:
                //day_friday.setBackgroundResource(R.drawable.style_round1);
                week_day = "Friday";
                getData();
                break;
            case R.id.txt_sat:
                //day_saturday.setBackgroundResource(R.drawable.style_round1);
                week_day = "Saturday";
                getData();
                break;
            case R.id.txt_sun:
                week_day = "Sunday";
                // day_sunday.setBackgroundResource(R.drawable.style_round1);
                getData();
                break;
            case R.id.btnSubmit:
                menuset = new HashSet<String>();
                menuset = sp.getStringSet("HEAVY", null);
                string = sp.getString("BASIC", null);
                count = sp.getInt("COUNT", 0);
                Log.d("count ", count + "");
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("Bread", selectedBread);
                intent.putExtra("Rice", selectedRice);
                intent.putExtra("Dal", selectedDal);
                intent.putExtra("Salt", selectedSalt);
                intent.putExtra("AmtOil", selectedAmtOil);
                intent.putExtra("OilType", selectedOil);
                intent.putExtra("Heat", selectedHeat);
                if (count > 1) {
                    startActivity(intent);
                } else if (!(string == null)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please select Subjis", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void getData() {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        urlRequest.setUrl("http://192.168.0.22:8001/routes/server/app/getSabji.php?type=" + type + "&dabba=" + tiffintype + "&meal=nonVeg&day=" + week_day);
        Log.d("getDataURL: ", "http://192.168.0.22:8001/routes/server/app/getSabji.php?type=" + type + "&dabba=" + tiffintype + "&meal=nonVeg&day=" + week_day);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("Response", response);
                // if(!response.contains("-1")) {
                try {
                    arrayList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        dataSubji = new DataSubji();
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                        dataSubji.subji = jsonArray1.getString(1);
                        arrayList.add(dataSubji);

                        Log.d("Data", dataSubji.subji);
                        recyclerView = view.findViewById(R.id.Listmenu);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                        if (tiffintype.equals("Basic")) {
                            adapterRadioButton = new AdapterRadioButton(getActivity(), arrayList);
                            recyclerView.setAdapter(adapterRadioButton);
                            adapterRadioButton.notifyDataSetChanged();
                        } else if (tiffintype.equals("Heavy")) {
                            adapterCheckbox = new AdapterCheckbox(getActivity(), arrayList);
                            recyclerView.setAdapter(adapterCheckbox);
                            adapterCheckbox.notifyDataSetChanged();
                        }
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    menuNotAvailable.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    menuNotAvailable.setText("oopss...,Menu is not provided for today");
                    menuNotAvailable.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
               /* }
                else
                {
                    Toast.makeText(getActivity(),"data not available",Toast.LENGTH_LONG).show();
                }*/

            }
        });
    }

    public void setData(String url, final String item) {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        urlRequest.setUrl(url);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("Responsespinner", response);
                try {


                    arrayList1 = new ArrayList<>();

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                        String str = jsonArray1.getString(1);
                        arrayList1.add(str);

                        if (item.equals("bread")) {
                            adapter_bread = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            spinner_indianBread.setPrompt("Bread");
                            spinner_indianBread.setAdapter(adapter_bread);
                            Log.d("Bread: ", arrayList1 + "");

                        } else if (item.equals("rice")) {
                            adapter_rice = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            spinner_rice.setAdapter(adapter_rice);

                            Log.d("Rice: ", arrayList1 + "");

                        } else if (item.equals("dal")) {
                            adapter_dal = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            spinner_dal.setAdapter(adapter_dal);
                            Log.d("Dal: ", arrayList1.toString());
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void selectedData() {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        Log.d("URL", "http://192.168.0.22:8001/routes/server/app/getAdminDabba.php?dabba=" + dabba + "&meal=vegSabji&day=" + week_day);
        urlRequest.setUrl("http://192.168.0.22:8001/routes/server/app/getAdminDabba.php?dabba=" + dabba + "&meal=vegSabji&day=" + week_day);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("ResponseSubji", response);
                listData = new HashSet<String>();
                arrayList1 = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    Log.d("Array", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                        dabba1 = jsonArray1.getString(0);
                        arrayList1.add(jsonArray1.getString(0));
                        listData.addAll(arrayList1);
                    }
                    editor.putString("SINGLE", dabba1);
                    editor.putStringSet("LIST", listData);
                    editor.commit();
                    Log.d("LIst", listData + "");
                    Log.d("DataSubji***", dabba1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
