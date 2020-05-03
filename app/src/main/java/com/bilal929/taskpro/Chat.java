package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton,iv_cm;
    TextView tv_cm;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference,reference1, reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        tv_cm=findViewById(R.id.tv_cm);
        iv_cm=findViewById(R.id.iv_cm);
//        tv_cm.setAllCaps(true);
        tv_cm.setText(UserData.chatName);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                "/o/ProfileImages%2F" + UserData.chatWith + "?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                .transform(new CircleTransform())
                .into(iv_cm);
        Firebase.setAndroidContext(this);
        reference1 = new Firebase(UserData.URL+"/chat/" + UserData.userid + "_" + UserData.chatWith);
        reference2 = new Firebase(UserData.URL+"/chat/" + UserData.chatWith + "_" + UserData.userid);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserData.userid);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserData.userid)){
                    String text = "You <font color='red'>red</font>";

                    addMessageBox("" + message, 1);
                }
                else{
                    addMessageBox(""+ message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        ImageView iv = new ImageView(Chat.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(95, 95);
        LinearLayout parent = new LinearLayout(Chat.this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.HORIZONTAL);

        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp2.weight = 1.0f;
        parent.setPadding(0,-10,10,-10);

        if(type == 1) {

            lp2.gravity = Gravity.RIGHT;
            lp3.gravity = Gravity.RIGHT;
            layoutParams.gravity=Gravity.RIGHT;
            parent.setGravity(View.FOCUS_RIGHT);

            lp3.setMargins(100,20,10,20);

            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setBackgroundResource(R.drawable.msg_out);
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                    "/o/ProfileImages%2F" + UserData.userid + "?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                    .transform(new CircleTransform())
                    .into(iv);


//            parent.setLayoutParams(lp2);

            parent.setBackgroundResource(R.drawable.msg_in);
            parent.addView(textView);
            parent.addView(iv);
        }
        else{
            lp2.gravity = Gravity.LEFT;
            lp3.gravity = Gravity.LEFT;
            layoutParams.gravity=Gravity.LEFT;

            lp3.setMargins(10,20,100,20);
            textView.setTextColor(Color.parseColor("#BC0D00"));
            textView.setBackgroundResource(R.drawable.msg_in);
            parent.setBackgroundResource(R.drawable.msg_out);
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                    "/o/ProfileImages%2F" + UserData.userid_post.get(UserData.position) + "?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                    .transform(new CircleTransform())
                    .into(iv);
            parent.addView(iv);
            parent.addView(textView);
        }
        textView.setLayoutParams(lp2);
        iv.setLayoutParams(layoutParams);
        layoutParams.topMargin=5;
        parent.setLayoutParams(lp3);
//        layout.setOrientation(LinearLayout.HORIZONTAL);


        layout.addView(parent);

        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
