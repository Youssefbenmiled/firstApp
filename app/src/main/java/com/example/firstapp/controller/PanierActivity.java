package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.firstapp.R;
import com.example.firstapp.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

public class PanierActivity extends AppCompatActivity {
    User user;
    RecyclerView rv_favorits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);


        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        BNV.setSelectedItemId(R.id.ItemPanier);

        user=getUser();

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ItemPanier:
                        break;
                    case R.id.ItemHome:
                        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("BUNDLEUSER",sendUser(user));
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemProduits:
                        Intent intent2=new Intent(getApplicationContext(),ProductActivity.class);
                        intent2.putExtra("BUNDLEUSER",sendUser(user));
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finishAffinity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public User getUser(){
        User user=null;
        Intent intent = getIntent();

        if(intent.getBundleExtra("BUNDLEUSER")!=null)
        {
            Bundle args = intent.getBundleExtra("BUNDLEUSER");
            user=(User)args.getSerializable("USER");


        }
        return user;
    }
    public Bundle sendUser(User us){

        Bundle args = new Bundle();

        if(us!=null)
        {
            args.putSerializable("USER",(Serializable)us);
        }
        return args;
    }
}