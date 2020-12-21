package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class LoginActivity extends AppCompatActivity {


    private EditText etUsername,etPassword;
    private Button btnLogin,btnInscrire;
    private TextView tvForget;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername=(EditText) findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btnLogin=(Button)findViewById(R.id.btnConnect);
        btnInscrire=(Button)findViewById(R.id.btnInscrire);
        tvForget=(TextView) findViewById(R.id.tvMDPoublie);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences= getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        String uid = preferences.getString("UID", null);
        if(uid!=null)
        {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }






        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ForgetActivity.class));

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();

                SignInUser(username,password);




            }
        });
        btnInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), InscrireActivity.class);

                startActivity(intent);

            }
        });


    }



    private void SignInUser(final String email, String password) {

        if(email.isEmpty()|| Patterns.EMAIL_ADDRESS.matcher(email).matches()==false){
            etUsername.setError("wrong email");
            etUsername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("wrong password");
            etPassword.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    redirectUser(email);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Failed to Login.Verify your data please",Toast.LENGTH_LONG).show();

                }
                //progressBar.setVisibility(View.GONE);


            }
        });

    }

    private void redirectUser(final String email) {


        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot objet:snapshot.getChildren()){

                    //User user=objet.getValue(User.class);
                    String em=objet.child("email").getValue(String.class);
                    if(em.equals(email)){

                        SharedPreferences preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("UID", objet.getKey());
                        editor.apply();
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                        return;

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage());
            }
        });

    }

    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finishAffinity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
