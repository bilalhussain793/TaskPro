package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

public class ShowTask extends AppCompatActivity {
ImageView imageView;
TextView name;
Button button_chat;

    Firebase reference1, reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        imageView=findViewById(R.id.iv_sh);
        name=findViewById(R.id.name_sh);
        button_chat=findViewById(R.id.bt_chat);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://notify-38a1e.firebaseio.com/users/" + UserData.userid + "/chat_list");
        reference2 = new Firebase("https://notify-38a1e.firebaseio.com/users/" + UserData.userid_post.get(UserData.position) + "/chat_list");

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                "/o/ProfileImages%2F" + UserData.userid_post.get(UserData.position) + "?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                .transform(new CircleTransform())
                .into(imageView);

        name.setText(UserData.title.get(UserData.position));
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.chatWith=UserData.userid_post.get(UserData.position);
                startActivity(new Intent(ShowTask.this,Chat.class));

                reference1.child(UserData.chatWith).setValue("0");
                reference2.child(UserData.userid).setValue("0");
            }
        });
    }
}
