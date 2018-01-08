package com.example.android.san;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class VegFragment extends Fragment {

    @InjectView(R.id.spinner_indianBread) Spinner spinner_indianBread;
    @InjectView(R.id.spinner_heat) Spinner spinner_heat;
    @InjectView(R.id.spinner_amountOfOil) Spinner spinner_amountOfOil;
    @InjectView(R.id.spinner_typeOfOil) Spinner spinner_typeOfOil;
    @InjectView(R.id.spinner_salt) Spinner spinner_salt;
    @InjectView(R.id.txt_mon) TextView day_monday;
    @InjectView(R.id.txt_tue) TextView day_tuesday;
    @InjectView(R.id.txt_wed) TextView day_wednesday;
    @InjectView(R.id.txt_thu) TextView day_thursday;
    @InjectView(R.id.txt_fri) TextView day_friday;
    @InjectView(R.id.txt_sat) TextView day_saturday;
    @InjectView(R.id.txt_sun) TextView day_sunday;
    @InjectView(R.id.txt_tiffininfo)
    TextView tiffin_tag;
    @InjectView(R.id.txt_menuNotavailable)
    TextView menuNotAvailable;
    @InjectView(R.id.spinner_rice) Spinner spinner_rice;
    @InjectView(R.id.spinner_dal) Spinner spinner_dal;
    @InjectView(R.id.btnSubmit)
    Button btnSubmit;
    @InjectView(R.id.btnCart)
    Button btnCart;
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
    boolean login = false;
    String selectedBread, selectedSalt, selectedRice, selectedDal, selectedOil, selectedAmtOil, selectedHeat, spinner_item;
    String item, str[], str1, str2, menu;
    String day, week_day, dabba1, string = null;
    ArrayAdapter adapter_bread, adapter_rice, adapter_dal;
    SharedPreferences sp;
    String type, tiffintype, dabba, t[], price;
    SharedPreferences.Editor editor;
    String auth_Id = "";
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

    public VegFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_veg, container, false);
        ButterKnife.inject(this, view);


        sp = getActivity().getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        auth_Id = sp.getString("AUTH_ID", "");
        Log.d("Login####", login + "");

        Log.d("Auth^^^^", auth_Id);
        editor = sp.edit();
        editor.clear();
        editor.commit();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        day = sdf.format(d);

        if(day.equals("Monday"))
        {
            day_monday.setSelected(true);
            day_monday.setBackgroundColor(Color.RED);
            week_day="Monday";
        }
        else
        if(day.equals("Tuesday"))
        {
            day_tuesday.setSelected(true);
            day_tuesday.setBackgroundColor(Color.RED);
            week_day="Tuesday";
            day_monday.setEnabled(false);
        }
        else
        if(day.equals("Wednesday"))
        {
            day_wednesday.setSelected(true);
            day_wednesday.setBackgroundColor(Color.RED);
            week_day="Wednesday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);

        }
        else
        if(day.equals("Thursday"))
        {
            day_thursday.setSelected(true);
            day_thursday.setBackgroundColor(Color.RED);
            week_day="Thursday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
        }
        else
        if(day.equals("Friday"))
        {
            day_friday.setSelected(true);
            day_friday.setBackgroundColor(Color.RED);
            week_day="Friday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
            day_thursday.setEnabled(false);
        }
        else
        if(day.equals("Saturday"))
        {
            day_saturday.setSelected(true);
            day_saturday.setBackgroundColor(Color.RED);
            week_day="Saturday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
            day_thursday.setEnabled(false);
            day_friday.setEnabled(false);
        }
        else
        if(day.equals("Sunday"))
        {
            day_sunday.setSelected(true);
            day_sunday.setBackgroundColor(Color.RED);
            week_day="Sunday";
            day_monday.setEnabled(false);
            day_tuesday.setEnabled(false);
            day_wednesday.setEnabled(false);
            day_thursday.setEnabled(false);
            day_friday.setEnabled(false);
            day_saturday.setEnabled(false);
        }
        btnCart.setText("ADD TO CART");

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
        price = getArguments().getString("Price");
        t = getArguments().getString("TiffinType").split(" ");
        tiffintype = t[0];
        Log.d("Typeveg", type);
        Log.d("TiffinType", tiffintype);
        Log.d("priceVeg", price);
        if (type.equals("semi flexible")) {
            str = type.split(" ");
            str1 = str[0];
            str2 = str[1];
            type = "semiFlexible";
            Log.d("Semi type", type);
            dabba = str1+ tiffintype;
            Log.d("Split", str1);
        }
        else {
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
        editor.putString("PRICE", price);

        editor.commit();
        String output = type.substring(0, 1).toUpperCase() + type.substring(1);
        tiffin_tag.setText(output + "/" + tiffintype);

        item = "bread";

        setData("http://192.168.0.107:8001/routes/server/app/getCommonItems.php?item=bread", item);

        item = "rice";

        setData("http://192.168.0.107:8001/routes/server/app/getCommonItems.php?item=rice", item);

        item = "dal";

        setData("http://192.168.0.107:8001/routes/server/app/getCommonItems.php?item=dal", item);

        Log.d("onCreateView: ", arrayList1 + "");
        getData();
        if (dabba.equals("semiHeavy") || dabba.equals("fixedBasic") || dabba.equals("fixedHeavy")) {
            selectedData();
        }
        return view;

    }

    @OnClick({R.id.txt_mon, R.id.txt_tue, R.id.txt_wed, R.id.txt_thu, R.id.txt_fri, R.id.txt_sat, R.id.txt_sun, R.id.btnSubmit, R.id.btnCart})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txt_mon:
                week_day="Monday";
                getData();
                break;
            case R.id.txt_tue:
                week_day="Tuesday";
                getData();
                break;
            case R.id.txt_wed:
                week_day="Wednesday";
                getData();
                break;
            case R.id.txt_thu:
                week_day="Thursday";
                getData();
                break;
            case R.id.txt_fri:
                week_day="Friday";
                getData();
                break;
            case R.id.txt_sat:
                week_day="Saturday";
                getData();
                break;
            case R.id.txt_sun:
                week_day="Sunday";
                getData();
                break;
            case R.id.btnSubmit:
                menuset = new HashSet<String>();
                menuset = sp.getStringSet("HEAVY", null);
                string = sp.getString("BASIC", null);
                count = sp.getInt("COUNT", 0);
                Log.d("count ", count + "");
                if (login) {
                    Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                    intent.putExtra("Bread", selectedBread);
                    intent.putExtra("Rice", selectedRice);
                    intent.putExtra("Dal", selectedDal);
                    intent.putExtra("Salt", selectedSalt);
                    intent.putExtra("AmtOil", selectedAmtOil);
                    intent.putExtra("OilType", selectedOil);
                    intent.putExtra("Heat", selectedHeat);
                    intent.putExtra("Price", price);
                    // intent.putExtra("Auth_Id", auth_Id);
                    if (count > 1) {
                        editor.putString("AUTH_ID", auth_Id);
                        editor.commit();
                        startActivity(intent);
                    } else if (!(string == null)) {
                        editor.putString("AUTH_ID", auth_Id);
                        editor.commit();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Please select Subjis", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intentlogin = new Intent(getContext(), LoginActivity.class);
                    intentlogin.putExtra("PARENT_ACTIVITY_NAME", "VegFragment");
                    startActivity(intentlogin);
                }
                break;
            case R.id.btnCart:

                String text = btnCart.getText().toString();
                count = sp.getInt("COUNT", 0);
                Log.d("Count", count + "");
                if (text.equals("ADD TO CART")) {
                    menuset = new HashSet<String>();
                    if (dabba.equals("semiHeavy")) {
                        menuset.add(sp.getString("SEMISTR1", null));
                        menuset.add(sp.getString("SEMISTR2", null));
                    } else {
                        menuset = sp.getStringSet("HEAVY", null);
                    }

                    Log.d("HeavyMenu", menuset + "");
                    string = sp.getString("BASIC", null);
                    //Log.d("BasicMenu",string);

                    //  Log.d("Authid",auth_Id);
                    final JSONObject orderData = new JSONObject();
                    try {
                        String typetext = type.substring(0, 1).toUpperCase();

                        orderData.put("tiffinPlan", typetext + type.substring(1));
                        orderData.put("tiffinType", tiffintype);
                        orderData.put("IndianBread", selectedBread);
                        orderData.put("Rice", selectedRice);
                        orderData.put("Dal", selectedDal);
                        orderData.put("Price", price);
                        Log.d("PriceO", price);
                        orderData.put("Quantity", "1");
                        orderData.put("Heat", selectedHeat);
                        orderData.put("Salt", selectedSalt);
                        orderData.put("AmountOfOil", selectedAmtOil);
                        orderData.put("OilType", selectedOil);
                        orderData.put("AuthId", auth_Id);

                        if (tiffintype.equals("Heavy")) {
                            orderData.put("menu", menuset);
                        } else {
                            orderData.put("menu", string);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (count > 1 || !(string == null)) {
                        if (login) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            Log.d("URLorder", "http://192.168.0.107:8001/routes/server/app/addToCart.rfa.php");
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.POST, "http://192.168.0.107:8001/routes/server/app/addToCart.rfa.php", orderData,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Log.d("ResponseOrder", response.getString("response"));
                                              /*  if (response.getString("response").equals("OK"))
                                                {
                                                    Intent intentGoToCart = new Intent(getContext(), GoToCart.class);
                                                    intentGoToCart.putExtra("AUTH_ID", auth_Id);
                                                    startActivity(intentGoToCart);
                                                 }*/

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    VolleyLog.d("Error: ", error.getMessage());
                                }
                            });
                            requestQueue.add(jsonObjReq);

                        btnCart.setText("GO TO CART");
                            Drawable icon = this.getResources().getDrawable(R.drawable.next);
                            // btnCart.setCompoundDrawablesWithIntrinsicBounds( null, null, icon, null );
                            btnCart.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                            Toast.makeText(getContext(), "Tiffin added into cart, now goto cart", Toast.LENGTH_LONG).show();

                        Log.d(orderData.toString(), "orderdata");
                        } else {
                            Intent intentlogin = new Intent(getContext(), LoginActivity.class);
                            intentlogin.putExtra("PARENT_ACTIVITY_NAME", "VegFragment");
                            startActivity(intentlogin);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please select Subjis", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Intent intentGoToCart = new Intent(getContext(), GoToCart.class);
                    intentGoToCart.putExtra("AUTH_ID", auth_Id);
                    startActivity(intentGoToCart);
                }
                break;

        }
    }
    public void getData()
    {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        urlRequest.setUrl("http://192.168.0.107:8001/routes/server/app/getSabji.php?type=" + type + "&dabba=" + tiffintype + "&meal=veg&day=" + week_day);
        Log.d("getDataURL: ", "http://192.168.0.107:8001/routes/server/app/getSabji.php?type=" + type + "&dabba=" + tiffintype + "&meal=veg&day=" + week_day);
        urlRequest.getResponse(new ServerCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Log.d("Response", response);

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

                            if (!(arrayList.size() == 0)) {
                                if (tiffintype.equals("Basic")) {
                                    adapterRadioButton = new AdapterRadioButton(getActivity(), arrayList);
                                    recyclerView.setAdapter(adapterRadioButton);
                                    adapterRadioButton.notifyDataSetChanged();
                                } else if (tiffintype.equals("Heavy")) {
                                    adapterCheckbox = new AdapterCheckbox(getActivity(), arrayList);
                                    recyclerView.setAdapter(adapterCheckbox);
                                    adapterCheckbox.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    menuNotAvailable.setText("oopss...,Menu is not provided for today");
                    menuNotAvailable.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    }


            }
        });
    }

    public void setData(String url, final String item)
    {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        urlRequest.setUrl(url);
        urlRequest.getResponse(new ServerCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Log.d("Responsespinner", response);
                try {


                    arrayList1=new ArrayList<>();

                    JSONArray jsonArray=new JSONArray(response);

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


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void selectedData()
    {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        Log.d("URL", "http://192.168.0.107:8001/routes/server/getAdminDabba.php?dabba=" + dabba + "&meal=vegSabji&day=" + week_day);
        urlRequest.setUrl("http://192.168.0.107:8001/routes/server/getAdminDabba.php?dabba=" + dabba + "&meal=vegSabji&day=" + week_day);
        urlRequest.getResponse(new ServerCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Log.d("ResponseSubji", response);
                listData = new HashSet<String>();
                arrayList1 = new ArrayList<>();
                try {
                    JSONArray jsonArray=new JSONArray(response);

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

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
