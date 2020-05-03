package com.bilal929.taskpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostTask extends AppCompatActivity {
    RadioButton radioButton;
    private int contentView;
    AlertDialog.Builder builder;

    String nm =UserData.userid;

    EditText title,etdescription,budget,locaton,unm;
    RadioGroup radioGroup;

    String Title,description,Budget,Location;
    String typeabd = null;
    String type="",typetask=null;
    String Type_of_task=null;
    Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_task);

        Firebase.setAndroidContext(this);

        title=findViewById(R.id.tit);
        etdescription=findViewById(R.id.desc);
        budget=findViewById(R.id.Budget);
        locaton=findViewById(R.id.location);
        radioGroup=findViewById(R.id.rd_group);

        sign=findViewById(R.id.btn_sign_in);

        unm=findViewById(R.id.usernames);
        builder = new AlertDialog.Builder(PostTask.this);

        unm.setText(UserData.userid);
        final Spinner spinner =  findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("Select Category");
        categories.add("Pick Up & Deliver");
        categories.add("Cleaning");
        categories.add("Gardening");
        categories.add("Home Services");
        categories.add("IT Services");
        categories.add("Other");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PostTask.this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title=title.getText().toString();
                description=etdescription.getText().toString();
                Budget=budget.getText().toString();
                Location=locaton.getText().toString();

                typeabd = spinner.getSelectedItem().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(PostTask.this, radioButton.getText().toString()+" Task Selected", Toast.LENGTH_SHORT).show();

                Type_of_task = radioButton.getText().toString();


                if(typeabd=="Pick Up & Deliver")
                {
                    type="delivery";

                }
                else if(typeabd=="Cleaning")
                {
                    type="cleaning";

                }
                else if(typeabd=="Gardening")
                {
                    type="gardening";

                }
                else if(typeabd=="Home Services")
                {
                    type="home service";

                }
                else if(typeabd=="IT Services")
                {
                    type="it service";

                }
                else if(typeabd=="Other")
                {
                    type="other";
                }
                else {
                    Toast.makeText(PostTask.this,"Please Catergory Type" ,Toast.LENGTH_LONG).show();
                }
              //  int value = Integer.parseInt(type);

                if(Title.length()==0){
                    title.setError("Empty!");
                }
                if(description.length()==0)
                {
                    etdescription.setError("Empty");
                }

                if(Budget.length()==0)
                {
                    budget.setError("Empty");
                }
                if(Location.length()==0)
                {
                    locaton.setError("Empty");
                }
                else
                {
                    PostTaskMethod(Title, description, Budget,Location,type,Type_of_task,nm);
                }

            }
        });

    }
    private void PostTaskMethod(final String Title, final String description, final String Budget,
                                final String Location,final String type,
                                final String type_of_task,final String Username) {
        final ProgressDialog pd = new ProgressDialog(PostTask.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://notify-38a1e.firebaseio.com/tasks/"+type_of_task+".json";

        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://notify-38a1e.firebaseio.com/users/"+Username+"/tasks");
                Firebase reference2 = new Firebase("https://notify-38a1e.firebaseio.com/tasks/"+type+"/"+Title+" iposted byi "+Username+" iprci "+Budget+" ilocationi "+Location);

                if(s.equals("null")) {
                    reference.child(Title).child("title").setValue(Title);
                    reference.child(Title).child("description").setValue(description);
                    reference.child(Title).child("budget").setValue(Budget);
                    reference.child(Title).child("Category").setValue(type_of_task);
                    reference.child(Title).child("task_type").setValue(type);
                    reference.child(Title).child("location").setValue(Location);

                    reference2.child("title").setValue(Title);
                    reference2.child("description").setValue(description);
                    reference2.child("budget").setValue(Budget);
                    reference2.child("Category").setValue(type_of_task);
                    reference2.child("task_type").setValue(type);
                    reference2.child("location").setValue(Location);
                    reference2.child("posted by").setValue(Username);
                    reference2.child("username").setValue(UserData.username);
                    reference2.child("phone").setValue(UserData.userContact);

                    Toast.makeText(PostTask.this, "Task Posted Successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(Title+" posted by "+Username)||!obj.has(Title)) {
                            reference.child(Title).child("title").setValue(Title);
                            reference.child(Title).child("description").setValue(description);
                            reference.child(Title).child("budget").setValue(Budget);
                            reference.child(Title).child("Category").setValue(type_of_task);
                            reference.child(Title).child("task_type").setValue(type);
                            reference.child(Title).child("location").setValue(Location);

                            reference2.child("title").setValue(Title);
                            reference2.child("description").setValue(description);
                            reference2.child("budget").setValue(Budget);
                            reference2.child("Category").setValue(type_of_task);
                            reference2.child("task_type").setValue(type);
                            reference2.child("location").setValue(Location);
                            reference2.child("posted by").setValue(Username);
                            reference2.child("username").setValue(UserData.username);
                            reference2.child("phone").setValue(UserData.userContact);
                            Toast.makeText(PostTask.this, "Task Posted Successfully", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(PostTask.this, "Task already exists", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                pd.dismiss();
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError );
                pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(PostTask.this);
        rQueue.add(request);



    }
}
