package com.example.firstapp.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

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


            BottomNavigationView BNV = findViewById(R.id.bottom_navigation);
            BNV.setSelectedItemId(R.id.ItemProduits);



            BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.ItemProduits:
                            break;
                        case R.id.ItemPanier:
                            Intent intent = new Intent(getApplicationContext(), PanierActivity.class);
                            //intent.putExtra("BUNDLEUSER", sendUser(user));
                            startActivity(intent);
                            overridePendingTransition(0, 0);

                            break;
                        case R.id.ItemHome:
                            Intent intent2 = new Intent(getApplicationContext(), HomeActivity.class);
                            //intent2.putExtra("BUNDLEUSER", sendUser(user));
                            startActivity(intent2);
                            overridePendingTransition(0, 0);
                            break;
                        default:
                            return false;
                    }
                    return true;
                }
            });
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
            menu.getItem(1).setEnabled(false);
            menu.getItem(2).setEnabled(false);


            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

            switch (item.getItemId()) {
                case R.id.ajout:

                    startActivity(new Intent(getApplicationContext(),AddProductActivity.class));
                    break;
                case R.id.update:
                    Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_LONG).show();
                    break;
                case R.id.delete:
                    Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
                    break;
                case R.id.disconnect:
                    disconnect();
                    break;
            }
            return true;


        }

        private void disconnect () {
            SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("UID", null);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            finish();
        }


    private void getImages(String uid) {

        FirebaseDatabase.getInstance().getReference().child("images").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objet:snapshot.getChildren()){
                    Upload upload=objet.getValue(Upload.class);
                    uploads.add(upload);
                }



                Adapter ADP=new Adapter(getApplicationContext(),uploads);
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