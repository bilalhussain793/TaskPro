package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowTask extends AppCompatActivity {
ImageView imageView;
TextView name,t_title,t_desc,t_phone;
Button button_chat;

    Firebase reference1, reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        imageView=findViewById(R.id.iv_sh);
        name=findViewById(R.id.name_sh);
        t_title=findViewById(R.id.t_name);
        t_desc=findViewById(R.id.t_desc);
        t_phone=findViewById(R.id.t_phone);
        button_chat=findViewById(R.id.bt_chat);

        Firebase.setAndroidContext(this);

        reference1 = new Firebase("https://notify-38a1e.firebaseio.com/users/" + UserData.userid + "/chat_list");
        reference2 = new Firebase("https://notify-38a1e.firebaseio.com/users/" + UserData.userid_post.get(UserData.position) + "/chat_list");

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                "/o/ProfileImages%2F" + UserData.userid_post.get(UserData.position) + "?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                .transform(new CircleTransform())
                .into(imageView);

//        name.setText(UserData.title.get(UserData.position));
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.chatWith=UserData.userid_post.get(UserData.position);
                startActivity(new Intent(ShowTask.this,Chat.class));

                reference1.child(UserData.chatWith).setValue("0");
                reference2.child(UserData.userid).setValue("0");
            }
        });
        String url = "https://notify-38a1e.firebaseio.com/tasks/"+UserData.ttype+".json";
        final ProgressDialog pd = new ProgressDialog(ShowTask.this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
//                 Toast.makeText(ShowTask.this, ""+s, Toast.LENGTH_LONG).show();
                if(s.equals("null")){
                    Toast.makeText(ShowTask.this, "user not found", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        JSONObject obj = new JSONObject(s);

                        name.setText(obj.getJSONObject(UserData.k_key.get(UserData.position)).getString("username"));
                        UserData.chatName=obj.getJSONObject(UserData.k_key.get(UserData.position)).getString("username");
                        t_title.setText(obj.getJSONObject(UserData.k_key.get(UserData.position)).getString("title"));
                        t_desc.setText(obj.getJSONObject(UserData.k_key.get(UserData.position)).getString("description"));
                        t_phone.setText(obj.getJSONObject(UserData.k_key.get(UserData.position)).getString("phone"));
//                            UserData.userid = user;
//                            UserData.password = pass;
//                            UserData.username=obj.getJSONObject(user).getString("name");
//                            UserData.userEmail = obj.getJSONObject(user).getString("email");
//                            UserData.userContact = obj.getJSONObject(user).getString("phone");
//                            UserData.accountType=obj.getJSONObject(user).getString("type");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                pd.dismiss();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                Toast.makeText(ShowTask.this, ""+volleyError, Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ShowTask.this);
        rQueue.add(request);
    }
}

