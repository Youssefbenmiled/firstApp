package com.example.firstapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.Produit;

public class DetailsActivity extends AppCompatActivity {
private Produit p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        if(intent.getBundleExtra("BUNDLE")!=null) {
            Bundle args = intent.getBundleExtra("BUNDLE");

             p = (Produit) args.getSerializable("PRODUIT");
            Toast.makeText(getApplicationContext(),p.getCurrentTime(),Toast.LENGTH_LONG).show();

        }
    }
}