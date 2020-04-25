package com.bilal929.taskpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskList extends AppCompatActivity {
    ListView lv;
    TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        lv=findViewById(R.id.lv);

        taskAdapter=new TaskAdapter(TaskList.this,UserData.title,UserData.location,UserData.userid_post,UserData.price);

        lv.setAdapter(taskAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TaskList.this, ""+UserData.userid_post.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
