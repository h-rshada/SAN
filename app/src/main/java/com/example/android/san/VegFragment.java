package com.example.android.san;


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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VegFragment extends Fragment {

    @InjectView(R.id.spinner_indianBread)
    Spinner spinner_indianBread;
    @InjectView(R.id.spinner_rice)
    Spinner spinner_rice;
    @InjectView(R.id.spinner_dal)
    Spinner spinner_dal;
    AdapterCheckbox adapter;
    /* @InjectView(R.id.Listmenu)*/
    RecyclerView recyclerView;
    UrlRequest urlRequest;
    Datacheckbox datacheckbox;
    List<Datacheckbox> arrayList;
    ArrayList arrayList1;
    View view;
    String item;
    ArrayAdapter adapter_bread, adapter_rice, adapter_dal;
    public VegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_veg, container, false);
        ButterKnife.inject(this, view);
      /*  ArrayList indianBread=new ArrayList();
        indianBread.add("Indian Bread");
        indianBread.add("Roti");
        indianBread.add("Paratha");
        indianBread.add("Poori");
        ArrayAdapter dataAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,indianBread);
        spinner_indianBread.setAdapter(dataAdapter);
        ArrayList rice=new ArrayList();
        rice.add("Rice");
        rice.add("Steam rice");
        ArrayAdapter dataAdapter1=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,rice);
        spinner_rice.setAdapter(dataAdapter1);

        ArrayList dal=new ArrayList();
        dal.add("Dal");
        dal.add("Dal fry");
        ArrayAdapter dataAdapter2=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,dal);
        spinner_dal.setAdapter(dataAdapter2);*/
        item = "bread";
        if (item.equals("bread")) {
            setData("http://192.168.0.22:8001/routes/server/getCommonItems.php?item=bread");
        }
        item = "rice";
        if (item.equals("rice")) {
            setData("http://192.168.0.22:8001/routes/server/getCommonItems.php?item=rice");
        }
        item = "dal";
        if (item.equals("dal")) {
            setData("http://192.168.0.22:8001/routes/server/getCommonItems.php?item=dal");
        }
       /* ArrayAdapter dataAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,arrayList1);
        spinner_indianBread.setAdapter(dataAdapter);*/
        Log.d("onCreateView: ", arrayList1 + "");
        getData();
        return view;

    }
    public void getData()
    {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        //Log.d("URL:","http://192.168.0.22:8000/routes/server/getSabji.php?type="+type+"&dabba="+tiffintype+"&meal="+meal+"&day="+day);
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
                    for(int i=0;i<jsonArray.length();i++){
                        datacheckbox=new Datacheckbox();
                        JSONArray jsonArray1=jsonArray.getJSONArray(i);
                        datacheckbox.strMenu=jsonArray1.getString(1);
                        arrayList.add(datacheckbox);
                    }
                    Log.d("Data",datacheckbox.strMenu);
                    recyclerView = view.findViewById(R.id.Listmenu);
                    adapter=new AdapterCheckbox(getActivity(),arrayList);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setData(String url)
    {

        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getContext());
        //Log.d("URL:","http://192.168.0.22:8000/routes/server/getSabji.php?type="+type+"&dabba="+tiffintype+"&meal="+meal+"&day="+day);
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
                            Log.d("item:1 ", item + "" + arrayList1);
                        } else if (item.equals("rice")) {
                            adapter_rice = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            spinner_rice.setAdapter(adapter_rice);
                            Log.d("Rice: ", arrayList1 + "");
                            Log.d("item:2 ", item);
                        } else if (item.equals("dal")) {
                            adapter_dal = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            spinner_dal.setAdapter(adapter_dal);
                            Log.d("Dal: ", arrayList1.toString());
                            Log.d("item:3", item);
                        }
                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
