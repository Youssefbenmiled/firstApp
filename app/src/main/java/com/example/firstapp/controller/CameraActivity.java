package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CameraActivity extends AppCompatActivity {
    ImageView iv_cam;
    //Button btn_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        iv_cam=(ImageView) findViewById(R.id.img_cam);
        //btn_camera=(Button)findViewById(R.id.btn_cam);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CameraActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);

        /*btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==100 ){
            if(data.getExtras()!=null) {
                SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
                String uid = preferences.getString("UID", "NOTHING");
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                iv_cam.setImageBitmap(captureImage);
                Uri uri = data.getData();

                StorageReference filepath= FirebaseStorage.getInstance().
                        getReference().child("Users").child(uid).child("photos")
                        .child(uri.getLastPathSegment());

                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "image uploaded", Toast.LENGTH_LONG).show();

                    }
                });




            }
            /*else
            {
                startActivity(new Intent(getApplicationContext(),ProductActivity.class));
            }*/

        }
    }
}