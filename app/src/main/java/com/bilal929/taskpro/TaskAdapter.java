package com.bilal929.taskpro;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> maintitle, loc1, userId, price1;
    private TextView title, loc, price;
    private ImageView iv;

    public TaskAdapter(Activity context, ArrayList<String> maintitle, ArrayList<String> loc1,
                       ArrayList<String> userid, ArrayList<String> price1) {
        super(context, R.layout.task_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.maintitle = maintitle;
        this.loc1 = loc1;
        this.userId = userid;
        this.price1 = price1;

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.task_layout, null, true);


        title = rowView.findViewById(R.id.task_name);
        loc = rowView.findViewById(R.id.location_tv);
        price = rowView.findViewById(R.id.prc);
        iv = rowView.findViewById(R.id.task_img);

        title.setText(maintitle.get(position));
        loc.setText(loc1.get(position));
        price.setText(price1.get(position));
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/notify-38a1e.appspot.com" +
                "/o/ProfileImages%2F" + userId.get(position) + "?alt=media&token=47eb0bd8-8ff0-4422-ac46-400aafa50caf")
                .transform(new CircleTransform())
                .into(iv);

        return rowView;

    }

}