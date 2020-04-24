package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class EarnMoney extends AppCompatActivity {
    ListView listView;
    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> userid=new ArrayList<String>();
    ArrayList<String> location=new ArrayList<String>();
    ArrayList<String> price=new ArrayList<String>();
    ArrayList<String> k_key=new ArrayList<String>();
    ArrayList<String> task_type=new ArrayList<String>();
    ArrayList<String> task_desc=new ArrayList<String>();
    String url = "https://notify-38a1e.firebaseio.com/tasks.json";
    int totalTasks = 0;
   // ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_money);


        listView=findViewById(R.id.lv);
//        title.add("ususu");
//        location.add("dmks");
//        userid.add("bilal123");
//        price.add("2333");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(EarnMoney.this);
        rQueue.add(request);
    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
         String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                if(!key.equals("")) {
                    String ttl=key.substring(0,key.indexOf("iposted"));
                    String I=key.substring(key.indexOf("iposted byi")).substring(12);
                    String L=key.substring(key.indexOf("ilocationi")).substring(11);
                    String P=key.substring(key.indexOf("iprci")).substring(6);

                    title.add(ttl);
                    userid.add(I.substring(0,I.indexOf(" iprci ")));
//                    task name and ID extracted above
                    location.add(L);
                    price.add("Rs: "+P.substring(0,P.indexOf(" ilocationi")));
                    Toast.makeText(this, ""+key, Toast.LENGTH_SHORT).show();
                    totalTasks++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalTasks <=1){

        }
        else{
            listView.setAdapter(new TaskAdapter(EarnMoney.this,title,location,userid,price));
       //   usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }
    }
}