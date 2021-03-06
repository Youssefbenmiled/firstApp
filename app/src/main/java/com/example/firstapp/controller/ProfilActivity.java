package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        if(intent.getBundleExtra("BUNDLE")!=null)
        {

            BNV.setSelectedItemId(R.id.ItemSearch);

        }
        else{
            BNV.setSelectedItemId(R.id.ItemProfil);

        }



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
                    case R.id.ItemProduits:
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ItemSearch:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemProfil:
                        startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }
    private TextView tv_email,tv_username,tv_phone;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        tv_email=findViewById(R.id.tv_adresse);
        tv_username=findViewById(R.id.tv_username);
        tv_phone=findViewById(R.id.tv_tel);

        Intent intent = getIntent();

        if(intent.getBundleExtra("BUNDLE")!=null) {
            Bundle args = intent.getBundleExtra("BUNDLE");
            user = (User) args.getSerializable("USER");

        }
        else
        {
            Gson gson = new Gson();
            String json = preferences.getString("USER", null);
            user =  gson.fromJson(json, User.class);
        }
        loadData(user);








    }

    private void loadData(User user) {
        if(user!=null) {
            tv_email.setText(user.getEmail());
            tv_username.setText(user.getUsername());
            tv_phone.setText(user.getTel());
        }
    }

    private void updateProfil(){


    }
}