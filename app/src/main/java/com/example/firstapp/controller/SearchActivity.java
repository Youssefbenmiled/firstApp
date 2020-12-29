package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        BNV.setSelectedItemId(R.id.ItemSearch);

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
                    case R.id.ItemProfil:
                        startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                        overridePendingTransition(0,0);
                        break;

                }
                return true;
            }
        });

    }
    private SearchView searchView;
    private RecyclerView rv_searchView;
    private ArrayList<User>listUsers;
    private ArrayList<String>listUsernames;
    private TextView introuvable;
    private ArrayAdapter<String> adapter;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView=findViewById(R.id.sv_search);
        rv_searchView=findViewById(R.id.rv_search);
        introuvable=findViewById(R.id.tv_miss);

        listUsers=new ArrayList<User>();
        listUsernames=new ArrayList<String>();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                    getUserViaUsername(newText.toLowerCase());





                return true;
            }
        });


    }

    private void getUserViaUsername(final String username) {
        if(username.length()==0){
            listUsernames.clear();
            listUsers.clear();
            rv_searchView.setAdapter(new SearchAdapter(getApplicationContext(),listUsers));
            return;
        }
        else
        {
            mDatabaseRef.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listUsernames);
                    Boolean trouve=false;
                    for(DataSnapshot snap:snapshot.getChildren()){

                        String ch=snap.child("username").getValue(String.class).toLowerCase();
                        if(ch.contains(username)){
                            introuvable.setText("");
                            trouve=true;
                            listUsers.add(snap.getValue(User.class));
                            listUsernames.add(ch);
                            rv_searchView.setAdapter(new SearchAdapter(getApplicationContext(),listUsers));
                            adapter.getFilter().filter(username);
                        }

                    }
                    if(!trouve){
                          listUsers.clear();
                          listUsernames.clear();
                          introuvable.setText("Aucun utilisateur trouvé");
                        rv_searchView.setAdapter(new SearchAdapter(getApplicationContext(),listUsers));

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        rv_searchView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



    }
}