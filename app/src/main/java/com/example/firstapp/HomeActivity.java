package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


     ArrayList<Produit>prods=new ArrayList<>();



    RecyclerView recyclerView;
    //String data1[] ,data2[];
    int images[]={R.drawable.ic_home,R.drawable.cplus};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=(RecyclerView)findViewById(R.id.recview);





        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        BNV.setSelectedItemId(R.id.ItemHome);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ItemHome:

                        break;
                    case R.id.ItemPanier:
                        Intent intent=new Intent(getApplicationContext(),PanierActivity.class);

                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemProduits:
                        Intent intent2=new Intent(getApplicationContext(),ProductActivity.class);
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

        getAllProducts();


    }
   









    public void getAllProducts(){
        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objet:snapshot.getChildren()){
                    for(DataSnapshot prod:objet.child("produits").getChildren()){
                        Produit p=prod.getValue(Produit.class);
                        prods.add(new Produit(p.getCategorie(),p.getNomProduit(),p.getFournisseur()));



                    }



                }

                Adapter ADP=new Adapter(getApplicationContext(),prods,images);
                recyclerView.setAdapter(ADP);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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




}
