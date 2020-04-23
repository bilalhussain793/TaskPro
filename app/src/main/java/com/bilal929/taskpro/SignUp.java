package com.bilal929.taskpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class SignUp extends AppCompatActivity {
    StorageReference storageReference;
//    FirebaseStorage storage;
    private Button btnRegister;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    EditText name,id,password,email,phone;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        storageReference = FirebaseStorage.getInstance().getReference();
//        storage = FirebaseStorage.getInstance();

        btnRegister = (Button) findViewById(R.id.r_btn);
        imageView = (ImageView) findViewById(R.id.img);
        name = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_pass);
        id = (EditText)findViewById(R.id.et_id);
        phone = (EditText)findViewById(R.id.et_phone);
        email = (EditText)findViewById(R.id.et_email);
        radioGroup=findViewById(R.id.r_group);
        TextView login = (TextView)findViewById(R.id.loginp);

        Firebase.setAndroidContext(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    Toast.makeText(SignUp.this, radioButton.getText().toString()+"Account Selected ", Toast.LENGTH_SHORT).show();
                    if(radioButton.getText().toString().equals("")){

                    }else {
                        reg(name.getText().toString(),id.getText().toString(),
                                email.getText().toString(),password.getText().toString(),
                                phone.getText().toString(),radioButton.getText().toString());
                    }
                }catch (Exception e){
                    Toast.makeText(SignUp.this, "Select Your Account Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage(String picname) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("ProfileImages/"+ picname);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    public void reg(final String uname, final String uid, final String u_email, final String pass, final String ph,final String type){
        if(uid.equals("")){
            id.setError("can't Null");
        }
        else if(uname.equals("")){
            name.setError("can't be blank");
        }
        else if(pass.equals("")){
            password.setError("can't be blank");
        }

        else if(!uid.matches("[A-Za-z0-9]+")){
            id.setError("only alphabet or number allowed");
        }
        else if(uid.length()<5){
            id.setError("at least 5 characters long");
        }

        else if(pass.length()<4){
            password.setError("at least 5 characters long");
        }
        else if(ph.equals("")){
            password.setError("Empty!");
        }
        else if(u_email.equals("")){
            password.setError("Empty!");
        }
        else {
            final ProgressDialog pd = new ProgressDialog(SignUp.this);
            pd.setMessage("Loading...");
            pd.show();

            String url = "https://notify-38a1e.firebaseio.com/users.json";

            final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    Firebase reference = new Firebase("https://notify-38a1e.firebaseio.com/users");

                    if(s.equals("null")) {
                        reference.child(uid).child("password").setValue(pass);
                        reference.child(uid).child("name").setValue(uname);
                        reference.child(uid).child("email").setValue(u_email);
                        reference.child(uid).child("id").setValue(uid);
                        reference.child(uid).child("phone").setValue(ph);
                        reference.child(uid).child("type").setValue(type);
                        uploadImage(uid);
                        Toast.makeText(SignUp.this, "registration successful", Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.has(uid)) {
                                reference.child(uid).child("password").setValue(pass);
                                reference.child(uid).child("name").setValue(uname);
                                reference.child(uid).child("email").setValue(u_email);
                                reference.child(uid).child("id").setValue(uid);
                                reference.child(uid).child("phone").setValue(ph);
                                reference.child(uid).child("type").setValue(type);
                                uploadImage(uid);
                                Toast.makeText(SignUp.this, "registration successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUp.this, "username already exists", Toast.LENGTH_LONG).show();
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

            RequestQueue rQueue = Volley.newRequestQueue(SignUp.this);
            rQueue.add(request);
        }

    }
}
