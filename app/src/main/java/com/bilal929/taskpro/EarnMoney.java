package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    ImageView gardening_iv,clean_iv,home_iv,deliver_iv,it_iv,other_iv;
    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> userid=new ArrayList<String>();
    ArrayList<String> location=new ArrayList<String>();
    ArrayList<String> price=new ArrayList<String>();
    ArrayList<String> k_key=new ArrayList<String>();
    ArrayList<String> task_type=new ArrayList<String>();
    ArrayList<String> task_desc=new ArrayList<String>();
    String url;
    int totalTasks = 0;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_money);
        final UserData userData=new UserData();
        pd = new ProgressDialog(EarnMoney.this);
        pd.setMessage("Loading...");

        gardening_iv=findViewById(R.id.gardening_iv);
        clean_iv=findViewById(R.id.clean_iv);
        home_iv=findViewById(R.id.home_iv);
        deliver_iv=findViewById(R.id.deliver_iv);
        it_iv=findViewById(R.id.it_iv);
        other_iv=findViewById(R.id.other_iv);

        gardening_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                url = "https://notify-38a1e.firebaseio.com/tasks/gardening.json";
                UserData.ttype="gardening";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
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
        });
        clean_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                url = "https://notify-38a1e.firebaseio.com/tasks/cleaning.json";
                UserData.ttype="cleaning";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
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
        });
        deliver_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                url = "https://notify-38a1e.firebaseio.com/tasks/delivery.json";
                UserData.ttype="delivery";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
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
        });
        home_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                url = "https://notify-38a1e.firebaseio.com/tasks/home service.json";
                UserData.ttype="home service";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
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
        });
        it_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                url = "https://notify-38a1e.firebaseio.com/tasks/it service.json";
                UserData.ttype="it service";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
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
        });
        other_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                url = "https://notify-38a1e.firebaseio.com/tasks/other.json";
                UserData.ttype="other";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(EarnMoney.this, ""+s, Toast.LENGTH_SHORT).show();
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
        });
        listView=findViewById(R.id.lv);

//        title.add("ususu");
//        location.add("dmks");
//        userid.add("bilal123");
//        price.add("2333");

    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
         String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                if(!key.equals("")) {
                    UserData.k_key.add(key);
                    String ttl=key.substring(0,key.indexOf("iposted"));
                    String I=key.substring(key.indexOf("iposted byi")).substring(12);
                    String L=key.substring(key.indexOf("ilocationi")).substring(11);
                    String P=key.substring(key.indexOf("iprci")).substring(6);

                    title.add(ttl);
                    UserData.title.add(ttl);
                    userid.add(I.substring(0,I.indexOf(" iprci ")));
                    UserData.userid_post.add(I.substring(0,I.indexOf(" iprci ")));

                    location.add(L);
                    UserData.location.add(L);
                    price.add("Rs: "+P.substring(0,P.indexOf(" ilocationi")));
                    UserData.price.add("Rs: "+P.substring(0,P.indexOf(" ilocationi")));

                    totalTasks++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            pd.dismiss();
        }

        if(totalTasks <=1){

        }
        else{

            TaskAdapter taskAdapter=new TaskAdapter(EarnMoney.this,title,location,userid,price);

            startActivity(new Intent(EarnMoney.this,TaskList.class));
            pd.dismiss();
//            listView.setAdapter(taskAdapter);

        }
    }
}