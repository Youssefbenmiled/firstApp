package com.example.firstapp.controller;
import com.bumptech.glide.Glide;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;

import com.example.firstapp.global.Constants;
import com.example.firstapp.model.*;
import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<Upload> images;

    //int images[];

    public Adapter(Context context, ArrayList<Upload> data){
        this.context=context;
        this.images=data;


    }



    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row,parent,false);
        MyViewHolder produitviewholder=new Adapter.MyViewHolder(view);
        return produitviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter.MyViewHolder holder, int position) {


        final Upload image=images.get(position);
        Log.i("ADAPTERURL",image.getImgUrl());
        /*Glide.with(context)
                //.load("gs://store-305a6.appspot.com/images/-MPA1TWro4K9wOtBWHnR.jpg")
                .load("https://firebasestorage.googleapis.com/v0/b/store-305a6.appspot.com/o/images%2F-MPB_avsg3PK9dgztEez.jpg?alt=media&token=6171b969-6693-4eda-b633-f2eb7f555af9")
                .load(image.getImgUrl())
                .into(holder.iv);*/
        Picasso.get()
                .load(image.getImgUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.iv);


        //holder.iv.setImageBitmap(unproduit);
        //holder.VerifyProducts(context);
        //holder.categorie.setText(unproduit.getCategorie());
        //holder.nom.setText(unproduit.getNomProduit());
        //holder.fournisseur.setText(unproduit.getFournisseur());
        //holder.nbpanier.setText(String.valueOf(unproduit.getNbPanier()));

        /*holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addProductToPanier(context,unproduit);

            }
        });*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Intent intent=new Intent(ct,PanierActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //ct.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //TextView categorie,nom,nbpanier,fournisseur;
        ImageView iv;
        //Button btnLike;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //categorie=itemView.findViewById(R.id.cat);
            //nom=itemView.findViewById(R.id.nomProduit);
            //nbpanier=itemView.findViewById(R.id.nb);
            //fournisseur=itemView.findViewById(R.id.frn);
            //btnLike=itemView.findViewById(R.id.likeBtn);

            iv=itemView.findViewById(R.id.imgview);

            /*iv.getLayoutParams().height = 250;
            iv.getLayoutParams().width = 250;
            iv.requestLayout();*/





        }


    }

    private void addProductToPanier (final Context context,Produit produit) {
        SharedPreferences preferences = context.getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        String uid = preferences.getString("UID", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Users").child(uid).child("produits").push().getKey();

        FirebaseDatabase.getInstance().getReference("Users")
                .child(uid)
                .child("panier")
                .child(key)
                .setValue(produit)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Produit ajouté au panier !", Toast.LENGTH_LONG).show();
                    }
                });


    }





}
