package com.example.android.san;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VegFragment extends Fragment {

    @InjectView(R.id.spinner_indianBread)
    Spinner spinner_indianBread;
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
    @InjectView(R.id.spinner_rice)
    Spinner spinner_rice;
    @InjectView(R.id.spinner_dal)
    Spinner spinner_dal;
    AdapterCheckbox adapterCheckbox;
    AdapterRadioButton adapterRadioButton;
    RecyclerView recyclerView;
    UrlRequest urlRequest;
    DataSubji dataSubji;
    List<DataSubji> arrayList,listData;
    ArrayList arrayList1;
    View view;
    String item,str[],str1;
    String day,week_day,dabba1;
    ArrayAdapter adapter_bread, adapter_rice, adapter_dal;

    String type, tiffintype, dabba,t[];

    public VegFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_veg, container, false);
        ButterKnife.inject(this, view);
        type = getArguments().getString("Type").toLowerCase();
        t = getArguments().getString("TiffinType").split(" ");
        tiffintype = t[0];
        Log.d("Type", type);
        Log.d("TiffinType", tiffintype);
        if (type.equals("semi flexible")) {
            str = type.split(" ");
            str1=str[0];
            dabba = str1+ tiffintype;
            Log.d("Split", str1);
        }
        else {
                dabba = type + tiffintype;
            }


        Log.d(dabba, "Dabba ");
        item = "bread";

        setData("http://192.168.0.22:8001/routes/server/getCommonItems.php?item=bread", item);

        item = "rice";

        setData("http://192.168.0.22:8001/routes/server/getCommonItems.php?item=rice", item);

        item = "dal";

        setData("http://192.168.0.22:8001/routes/server/getCommonItems.php?item=dal", item);

        Log.d("onCreateView: ", arrayList1 + "");
        getData();
        if(dabba.equals("semiHeavy") || dabba.equals("fixedBasic")|| dabba.equals("fixedHeavy")) {
            selectedData();
        }

        return view;

    }
    public void getData()
    {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
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
        }
        else
        if(day.equals("Wednesday"))
        {
            day_wednesday.setSelected(true);
            day_wednesday.setBackgroundColor(Color.RED);
            week_day="Wednesday";
        }
        else
        if(day.equals("Thursday"))
        {
            day_thursday.setSelected(true);
            day_thursday.setBackgroundColor(Color.RED);
            week_day="Thursday";
        }
        else
        if(day.equals("Friday"))
        {
            day_friday.setSelected(true);
            day_friday.setBackgroundColor(Color.RED);
            week_day="Friday";
        }
        else
        if(day.equals("Saturday"))
        {
            day_saturday.setSelected(true);
            day_saturday.setBackgroundColor(Color.RED);
            week_day="Saturday";
        }
        else
        if(day.equals("Sunday"))
        {
            day_sunday.setSelected(true);
            day_sunday.setBackgroundColor(Color.RED);
            week_day="Sunday";
        }
        urlRequest.setUrl("http://192.168.0.22:8001/routes/server/getSabji.php?type=flexible&dabba=basic&meal=veg&day=Sunday");
        urlRequest.getResponse(new ServerCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Log.d("Response", response);
                try {

                    arrayList = new ArrayList<>();
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        dataSubji = new DataSubji();
                        JSONArray jsonArray1=jsonArray.getJSONArray(i);
                        dataSubji.subji = jsonArray1.getString(1);
                        arrayList.add(dataSubji);
                    }
                    Log.d("Data", dataSubji.subji);
                    recyclerView = view.findViewById(R.id.Listmenu);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    if (tiffintype.equals("Basic"))
                    {
                        adapterRadioButton = new AdapterRadioButton(getActivity(), arrayList);
                        recyclerView.setAdapter(adapterRadioButton);
                        adapterRadioButton.notifyDataSetChanged();
                    }
                    else if(tiffintype.equals("Heavy"))
                    {

                        adapterCheckbox = new AdapterCheckbox(getActivity(), arrayList);
                        recyclerView.setAdapter(adapterCheckbox);
                        adapterCheckbox.notifyDataSetChanged();
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
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
        urlRequest.setUrl("http://192.168.0.22:8001/routes/server/getAdminDabba.php?dabba="+dabba);
        urlRequest.getResponse(new ServerCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Log.d("ResponseSubji", response);
                try {

                    dataSubji=new DataSubji();
                    JSONArray jsonArray=new JSONArray(response);
                    Log.d("Array",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        dabba1= (String) jsonArray.get(i);
                    }
                   dataSubji.setSelectedSubji(dabba1);
                    dataSubji.getSelectedSubji();
                    Log.d("Selected", dataSubji.getSelectedSubji());
                    Log.d("DataSubji",dabba1);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

}
