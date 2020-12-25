package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.firstapp.R;
import com.example.firstapp.model.Produit;
import com.example.firstapp.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {


     private ArrayList<Upload>imgLists;



     private RecyclerView recyclerView;
     //private ImageView iv;
    //int images[]={R.drawable.ic_home,R.drawable.cplus}; /
     private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=(RecyclerView)findViewById(R.id.recview);

        imgLists=new ArrayList<>();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference("images");

        //SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        //String uid = preferences.getString("UID", "NOTHING");
        getImages();


    }

    private void getImages() {

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren()){
                    for(DataSnapshot snapIN:snap.getChildren()){

                        Upload upload=snap.child(snapIN.getKey()).getValue(Upload.class);
                        imgLists.add(upload);
                    }
                }
                Adapter ADP=new Adapter(getApplicationContext(),imgLists);
                recyclerView.setAdapter(ADP);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finishAffinity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        BNV.setSelectedItemId(R.id.ItemHome);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ItemHome:

                        break;
                    case R.id.ItemPanier:
                        Intent intent=new Intent(getApplicationContext(), PanierActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemProduits:
                        Intent intent2=new Intent(getApplicationContext(), ProductActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

    }
}
