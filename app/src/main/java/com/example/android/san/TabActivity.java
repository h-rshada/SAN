package com.example.android.san;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {

   String type,tiffintype="basic";
   String meal="veg";
   String day="Sunday";
   RecyclerView recyclerView;
   AdapterRadioButton adapter;
   ArrayList<DataSubji> arrayList;
   DataSubji dataSubji;
   UrlRequest urlRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        type=getIntent().getStringExtra("Type").toLowerCase();
        tiffintype = getIntent().getStringExtra("TiffinType").toLowerCase();
        Log.d("Type", type);
        Log.d("TiffinType", tiffintype);
        getData();

    }


   public void getData()
   {
       urlRequest = UrlRequest.getObject();
       urlRequest.setContext(getBaseContext());
       //  Log.d("URL:","http://192.168.0.22:8000/routes/server/getSabji.php?type="+type+"&dabba="+tiffintype+"&meal="+meal+"&day="+day);
       urlRequest.setUrl("http://192.168.0.22:8000/routes/server/getSabji.php?type=flexible&dabba=basic&meal=veg&day=Sunday");
       urlRequest.getResponse(new ServerCallback()
       {
           @Override
           public void onSuccess(String response)
           {
               Log.d("Response", response);
               try {

                   arrayList=new ArrayList<>();

                   JSONArray jsonArray=new JSONArray(response);
                   for(int i=0;i<jsonArray.length();i++){
                       dataSubji=new DataSubji();
                       JSONArray jsonArray1=jsonArray.getJSONArray(i);
                       dataSubji.subji=jsonArray1.getString(1);
                       arrayList.add(dataSubji);
                   }
                   Log.d("Data", String.valueOf(arrayList.size()));
                   recyclerView = findViewById(R.id.recyclerData);
                   adapter=new AdapterRadioButton(TabActivity.this,arrayList);
                   recyclerView.setLayoutManager(new GridLayoutManager(TabActivity.this, 2));
                   recyclerView.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
               } catch (JSONException e)
               {
                   e.printStackTrace();
               }
           }
       });
   }
}
