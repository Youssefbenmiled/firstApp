package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
        //iv=findViewById(R.id.iv_try);
        imgLists=new ArrayList<>();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference("images");

        //getAllProducts();
        getImages();


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
                        //intent2.putExtra("BUNDLEUSER",sendUser(user));
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

    private void getImages() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren()){
                    Upload upload=snap.getValue(Upload.class);
                    imgLists.add(upload);
                    //Toast.makeText(getApplicationContext(),upload.getImgUrl(),Toast.LENGTH_LONG).show();
                    Log.d("snaps",snap.toString());
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



        /*mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    UploadTask upload=snap.getValue(UploadTask.class);
                    imgLists.add(upload);

                }
                Adapter ADP=new Adapter(getApplicationContext(),imgLists);
                recyclerView.setAdapter(ADP);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }


    public void getAllProducts(){
        /*mDatabaseRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objet:snapshot.getChildren()){
                    for(DataSnapshot prod:objet.child("produits").getChildren()){
                        Produit p=prod.getValue(Produit.class);
                        //prods.add(p);
                    }
                }

                /*Adapter ADP=new Adapter(getApplicationContext(),prods,images);
                recyclerView.setAdapter(ADP);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }
/*
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finishAffinity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}
