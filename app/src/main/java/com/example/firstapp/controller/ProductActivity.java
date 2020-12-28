package com.example.firstapp.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.firstapp.R;
import com.example.firstapp.model.Produit;
import com.example.firstapp.model.Upload;
import com.example.firstapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        BNV.setSelectedItemId(R.id.ItemProduits);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ItemHome:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemPanier:
                        startActivity(new Intent(getApplicationContext(), PanierActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ItemProfil:
                        startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemSearch:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });

    }
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<Upload> uploads=new ArrayList<>();
    private TextView tv_prd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product);

            recyclerView = findViewById(R.id.rv_Products);
            toolbar = findViewById(R.id.toolb);
            tv_prd=findViewById(R.id.tv_null);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);



            SharedPreferences preferences= getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
            String uid = preferences.getString("UID", "NOTHING");
            getImages(uid);


        }




    @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                finishAffinity();
                return true;
            }

            return super.onKeyDown(keyCode, event);
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            getMenuInflater().inflate(R.menu.menu, menu);
            //menu.getItem(1).setEnabled(false);
            //menu.getItem(2).setEnabled(false);


            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
            StorageReference stRef=FirebaseStorage.getInstance().getReference();
            SharedPreferences preferences= getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
            String uid = preferences.getString("UID", "NOTHING");
            switch (item.getItemId()) {
                case R.id.ajout:
                    startActivity(new Intent(getApplicationContext(),AddProductActivity.class));
                    break;
                case R.id.update:
                    updateProduct(uid,"-MPMV6WhAwhQuUSgmdf0",dbRef);
                    break;
                case R.id.delete:
                    deleteProduct(uid,"-MPMWZG7ivF6-a_6khg0",stRef,dbRef);
                    break;
                case R.id.disconnect:
                    disconnect();
                    break;
            }
            return true;


        }


    private void updateProduct(String uid,String key,DatabaseReference dbRef) {
        //Map<String,Object>map=new HashMap<>();

        Produit produit=new Produit("adresse33",false,"01/01/2021");

        //map.put("produit",new Produit("adresse2",false,"01/01/2021"));

        dbRef.child("Users").child(uid).child("produits").child(key).setValue(produit)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","UPDATE SUCCESS");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","UPDATE FAILED");
            }
        });


    }


    private void deleteProduct(String uid,String key,StorageReference stRef,DatabaseReference dbRef) {
        DatabaseReference produit = dbRef.child("Users").child(uid).child("produits").child(key);
        DatabaseReference produitImg = dbRef.child("images").child(uid).child(key);
        StorageReference image = stRef.child("-MPMWZG7ivF6-a_6khg0.jpg");
        produit.removeValue();
        produitImg.removeValue();

        image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void disconnect () {
            SharedPreferences preferences= getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("UID", null);
            editor.putString("USER", null);
            editor.putBoolean("USER_CONNECTED", false);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            finishAffinity();
        }


    private void getImages(String uid) {

        FirebaseDatabase.getInstance().getReference().child("images").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objet:snapshot.getChildren()){
                    Upload upload=objet.getValue(Upload.class);
                    uploads.add(upload);
                }



                Adapter ADP=new Adapter(getApplicationContext(),uploads,"produit");
                recyclerView.setAdapter(ADP);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                if(uploads.size()==0){
                    tv_prd.setText("Aucun produit à votre disposition");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}