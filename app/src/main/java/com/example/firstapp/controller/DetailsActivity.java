package com.example.firstapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.Produit;
import com.example.firstapp.model.Upload;

public class DetailsActivity extends AppCompatActivity {
    private Produit produit;
    private Upload upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        if(intent.getBundleExtra("BUNDLE")!=null) {
            Bundle args = intent.getBundleExtra("BUNDLE");

            produit = (Produit) args.getSerializable("PRODUIT");
            upload = (Upload) args.getSerializable("IMAGE");

            Toast.makeText(getApplicationContext(),produit.getFournisseur(),Toast.LENGTH_LONG).show();

        }
    }
}