package com.example.firstapp.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.Produit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView BNV=findViewById(R.id.bottom_navigation);
        BNV.setSelectedItemId(R.id.ItemProduits);

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
                    case R.id.ItemProfil:
                        startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemSearch:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ItemProduits:
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }

    private EditText txt_date,txt_adresse;
    private TextView tv_date;
    private Button btn_continue;

    DatePickerDialog.OnDateSetListener setListener;

    private Boolean disp;
    private Produit produit=new Produit();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        txt_adresse= (EditText) findViewById(R.id.edit_adresse);


        txt_date= (EditText) findViewById(R.id.et_dateDispo);
        tv_date= (TextView) findViewById(R.id.tv_date);
        btn_continue= (Button) findViewById(R.id.go_to_img);


        disp=true;


        if(disp)
        {
            tv_date.setVisibility(View.GONE);
            txt_date.setVisibility(View.GONE);
        }
        else
        {
            tv_date.setVisibility(View.VISIBLE);
            txt_date.setVisibility(View.VISIBLE);
        }


         Calendar calendrier=Calendar.getInstance();


         final int year=calendrier.get(Calendar.YEAR);
         final int month=calendrier.get(Calendar.MONTH);
         final int day=calendrier.get(Calendar.DAY_OF_MONTH);
         /*
         tv_date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog DPD=new DatePickerDialog(AddProductActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                 DPD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 DPD.show();
             }
         });

          */

         /*setListener=new DatePickerDialog.OnDateSetListener() {
             @Override
             public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                 String date=dayOfMonth+"/"+month+1+"/"+year;
                 tv_date.setText(date);

             }
         };*/

         txt_date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog DPD=new DatePickerDialog(AddProductActivity.this,
                         new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int day) {
                         String date=day+"/"+(month+1)+"/"+year;

                         txt_date.setText(date);
                     }
                 },year,month,day);
                 DPD.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                 DPD.show();
             }
         });

         btn_continue.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Produit p=verifyForm();
                 if(p!=null){
                     Intent intent=new Intent(getApplicationContext(),CameraActivity.class);
                     Bundle args = new Bundle();
                     args.putSerializable("PRODUIT",(Serializable)p);
                     intent.putExtra("BUNDLE",args);
                     startActivity(intent);
                 }


             }
         });

    }



    private Produit verifyForm() {


        final String adresse=txt_adresse.getText().toString().trim();
        final String dateDis=txt_date.getText().toString().trim();




        if(adresse.isEmpty()){
            txt_adresse.setError("Votre adresse est obligatoire");
            txt_adresse.requestFocus();
            return null;
        }
        if(disp==false)
        {
            if(dateDis.isEmpty() ){
                txt_date.setError("La date de disponibilité est obligatoire");
                txt_date.requestFocus();
                return null;
            }
            if(!convertDate(dateDis)){
                txt_date.setError("La date est invalide");
                txt_date.requestFocus();
                return null;
            }
            if(convertCompare(dateDis)>0){
                    txt_date.setError("La date est invalide");
                    txt_date.requestFocus();
                    return null;
                }

        }


        if(disp){
            return new Produit(adresse,true,null);
        }
        else
        {
            return new Produit(adresse,false,dateDis);

        }


    }

    private int convertCompare(String d2) {
        String d1=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date date1=format.parse(d1);
            Date date2=format.parse(d2);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 100;


    }

    private boolean convertDate(String dateDis) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            date = sdf.parse(dateDis);
            if (!dateDis.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return false;
        } else {

            return true;
        }
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_yes:
                if (checked)
                {
                    tv_date.setVisibility(View.GONE);
                    txt_date.setVisibility(View.GONE);
                    disp=true;
                }

                    break;
            case R.id.radio_no:
                if (checked)
                {
                    tv_date.setVisibility(View.VISIBLE);
                    txt_date.setVisibility(View.VISIBLE);
                    disp=false;

                }
                    break;
        }

    }
}