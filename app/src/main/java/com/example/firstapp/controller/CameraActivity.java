package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.WindowManager;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {
    private ImageView iv_cam;
    private Button btn_camera;
    private Bitmap captureImage;
    private StorageReference mStorageRef;
    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        iv_cam=(ImageView) findViewById(R.id.img_cam);
        mStorageRef=FirebaseStorage.getInstance().getReference();
        btn_camera=(Button)findViewById(R.id.btn_cam);
        btn_camera.setVisibility(View.GONE);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CameraActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });

    }

    private void uploadFile() {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();

        captureImage.compress(Bitmap.CompressFormat.JPEG,100,stream);


        Intent intent=getIntent();
        String key=intent.getStringExtra("key");

        StorageReference riversRef = mStorageRef.child("images/"+key);
        byte []b=stream.toByteArray();

        riversRef.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                        Toast.makeText(getApplicationContext(),"onSuccess",Toast.LENGTH_LONG).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();

                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==100 ){
            //if(data.getData()!=null) {
                btn_camera.setVisibility(View.VISIBLE);
                SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
                String uid = preferences.getString("UID", "NOTHING");
                captureImage = (Bitmap) data.getExtras().get("data");
                iv_cam.setImageBitmap(captureImage);


            /*}
            else
            {
                Toast.makeText(getApplicationContext(),"HANI FEL ELSE",Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(),ProductActivity.class));
            }*/

        }
    }
}