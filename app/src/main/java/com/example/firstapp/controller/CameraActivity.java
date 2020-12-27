package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
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
import com.example.firstapp.model.Produit;
import com.example.firstapp.model.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    private ImageView iv_cam;
    private Button btn_camera;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Uri ImageUri;
    private Produit produit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        iv_cam=(ImageView) findViewById(R.id.img_cam);
        btn_camera=(Button)findViewById(R.id.btn_cam);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("images");

        Intent intent = getIntent();
        if(intent.getBundleExtra("BUNDLE")!=null) {
            Bundle args = intent.getBundleExtra("BUNDLE");

            produit = (Produit) args.getSerializable("PRODUIT");
        }


            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CameraActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        openGallery();


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
                String uid = preferences.getString("UID", "NOTHING");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String key = database.getReference("Users").child(uid).child("produits").push().getKey();

                uploadFile(key,uid);
                addProduct(key,uid,produit);
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finishAffinity();


            }
        });

    }




    private void addProduct(String key,String uid,Produit produit) {
        FirebaseDatabase.getInstance().getReference("Users")
                .child(uid)
                .child("produits")
                .child(key)
                .setValue(produit)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


    }

    private String getFileExtension(Uri uri){
        ContentResolver CR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    private void uploadFile(final String key, final String uid) {
        if(ImageUri!=null){

            mStorageRef.child(key+"."+getFileExtension(ImageUri))
                    .putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Upload upload=new Upload(uri.toString(),key);

                            mDatabaseRef.child(uid).child(key).setValue(upload);
                            Toast.makeText(getApplicationContext(), "Produit ajouté!", Toast.LENGTH_LONG).show();


                        }
                    });
                    //Toast.makeText(getApplicationContext(),"Image ajoutée",Toast.LENGTH_LONG).show();
                    //mProgressBar.setProgress(0);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    //mProgressBar.setProgress((int)progress);
                }
            });

        }
    }

    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);


    }

    /*private void uploadFile() {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();

        captureImage.compress(Bitmap.CompressFormat.JPEG,100,stream);




        StorageReference riversRef = mStorageRef.child("images/"+"img");
        byte []b=stream.toByteArray();

        riversRef.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(getApplicationContext(),"Image ajoutée",Toast.LENGTH_LONG).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.i("ERROR",exception.getMessage());
                        Toast.makeText(getApplicationContext(),"Echec",Toast.LENGTH_LONG).show();

                    }
                });


    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100) {


            if (data!= null) {

                ImageUri = data.getData();
                iv_cam.setImageURI(ImageUri);
                //btn_camera.setVisibility(View.VISIBLE);
                //SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
                //String uid = preferences.getString("UID", "NOTHING");


                //Picasso.get().load(ImageUri).into(iv_cam);

                //captureImage = (Bitmap) data.getExtras().get("data");
                //iv_cam.setImageBitmap(captureImage);


            }
            else
            {
                startActivity(new Intent(getApplicationContext(),AddProductActivity.class));
                finishAffinity();
            }

        }
    }
}