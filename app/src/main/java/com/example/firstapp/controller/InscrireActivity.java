package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.firstapp.R;
import com.example.firstapp.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InscrireActivity extends AppCompatActivity {

    private EditText var_username,var_pass1,var_pass2,var_phone,var_email;
    private Button btnConfirm;
    private ArrayList<User>Users=new ArrayList<>();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrire);


        var_username=findViewById(R.id.etUsername);
        var_pass1=findViewById(R.id.etPassword);
        var_pass2=findViewById(R.id.etPasswordVerif);
        var_phone=findViewById(R.id.etPhone);
        var_email=findViewById(R.id.etEmail);
        btnConfirm=findViewById(R.id.btnConfirm);
        mAuth = FirebaseAuth.getInstance();
        //btnConfirm.setEnabled(true);



        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });

    }





    private void registerUser() {
        final String username=var_username.getText().toString().trim();
        final String em=var_email.getText().toString().trim();
        String pass1=var_pass1.getText().toString().trim();
        String pass2=var_pass2.getText().toString().trim();
        final String tel=var_phone.getText().toString().trim();

        if(username.isEmpty() ){
            var_username.setError("Nom utilisateur vide");
            var_username.requestFocus();
            return;
        }

        if(username.length()<5 ){
            var_username.setError("5 caracteres minimum");
            var_username.requestFocus();
            return;
        }
        if(trouve(username)){
            var_username.setError("Nom utilisateur existe deja");
            var_username.requestFocus();
            return;
        }

        if(em.isEmpty()|| Patterns.EMAIL_ADDRESS.matcher(em).matches()==false){
            var_email.setError("wrong email");
            var_email.requestFocus();
            return;
        }
        if(pass1.isEmpty()){
            var_pass1.setError("Mot de passe vide");
            var_pass1.requestFocus();
            return;
        }
        if(pass1.length()<6){
            var_pass1.setError("6 caractéres au minimum");
            var_pass1.requestFocus();
            return;
        }
        if(!pass2.equals(pass1)){
            var_pass2.setError("Vérifier votre mot de passe");
            var_pass2.requestFocus();
            return;
        }
        if(tel.length()!=8){
            var_phone.setError("Vérifier votre numéro de téléphone");
            var_phone.requestFocus();
            return;
        }
        char c=tel.charAt(0);
        if (Integer.parseInt(String.valueOf(c))!=2 && Integer.parseInt(String.valueOf(c))!=5 &&Integer.parseInt(String.valueOf(c))!=9) {


            var_phone.setError("Vérifier votre numéro de téléphone");
            var_phone.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(em,pass1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //btnConfirm.setEnabled(false);
                            final User user=new User(username,em,tel,new ArrayList<Produit>(),new ArrayList<Produit>());
                            final String ch=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //Constants.UID=ch;
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(ch)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("UID", ch);
                                        editor.apply();

                                        Toast.makeText(getApplicationContext(),"User registred succefully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();


                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"registration failed.Please try again",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"registration failed.Please try again",Toast.LENGTH_LONG).show();

                        }
                    }
                });




    }

    private boolean trouve(final String username) {
        final Boolean[] exist = {false};
        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot objet:snapshot.getChildren()){
                    String user=objet.child("username").getValue(String.class);
                    if(user.equals(username)){

                        exist[0] =true;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage());
            }
        });
        return exist[0];


    }


}