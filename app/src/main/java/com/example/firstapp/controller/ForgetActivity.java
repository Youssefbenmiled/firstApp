package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
    EditText var_email;
    Button btn_continue;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        var_email=findViewById(R.id.etUsername);
        btn_continue=findViewById(R.id.btnForget);
        mAuth=FirebaseAuth.getInstance();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailForget();

            }
        });
    }

    private void sendEmailForget() {
        String em = var_email.getText().toString().trim();

        if(em.isEmpty()|| Patterns.EMAIL_ADDRESS.matcher(em).matches()==false){
            var_email.setError("wrong email");
            var_email.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {

                    Toast.makeText(getApplicationContext(),"Check your email address please",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Something went wrong! try again please.",Toast.LENGTH_LONG).show();

                }
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //progressBar.setVisibility(View.GONE);
                finish();



            }
        });


    }
}