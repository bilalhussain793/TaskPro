package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {
ImageView profileImage,earn_money,post_task;
TextView tv_name,tv_phone,tv_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileImage=findViewById(R.id.iv);
        earn_money=findViewById(R.id.ern_m);
        post_task=findViewById(R.id.post_task);
        tv_name=findViewById(R.id.tv_name);
        tv_phone=findViewById(R.id.tv_phone);
        tv_email=findViewById(R.id.tv_em);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                "/o/ProfileImages%2F"+UserData.userid+"?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                .transform(new CircleTransform()).into(profileImage);
        tv_name.setText(UserData.username);
        tv_phone.setText(UserData.userContact);
        tv_email.setText(UserData.userEmail);
        earn_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,EarnMoney.class));
            }
        });
        post_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,PostTask.class));
            }
        });

    }
}
