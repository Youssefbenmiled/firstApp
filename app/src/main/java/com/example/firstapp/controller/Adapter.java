package com.example.firstapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<Produit> produits;
    ArrayList<Produit> prods=new ArrayList<>();

    int images[];

    public Adapter(Context context, ArrayList<Produit> data1, int imgs[]){
        this.context=context;
        this.produits=data1;

        this.prods=new ArrayList<>();
        this.images=imgs;

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


        final Produit unproduit=produits.get(position);
        //holder.iv.setImageResource(images[0]);
        //holder.VerifyProducts(context);
        holder.categorie.setText(unproduit.getCategorie());
        holder.nom.setText(unproduit.getNomProduit());
        holder.fournisseur.setText(unproduit.getFournisseur());
        //holder.nbpanier.setText(String.valueOf(unproduit.getNbPanier()));
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToPanier(context,unproduit);

            }
        });


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
        return produits.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categorie,nom,nbpanier,fournisseur;
        ImageView iv;
        Button btnLike;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categorie=itemView.findViewById(R.id.cat);
            nom=itemView.findViewById(R.id.nomProduit);
            //nbpanier=itemView.findViewById(R.id.nb);
            fournisseur=itemView.findViewById(R.id.frn);
            btnLike=itemView.findViewById(R.id.likeBtn);

            iv=itemView.findViewById(R.id.imgview);
            iv.getLayoutParams().height = 250;
            iv.getLayoutParams().width = 250;
            iv.requestLayout();


            //btnLike.setVisibility(View.GONE);



        }
        /*public void VerifyProducts(Context context) {
            SharedPreferences preferences= context.getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
            final String uid = preferences.getString("UID", null);

            FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot objet : snapshot.getChildren()) {
                        if(objet.getKey().equals(uid)){
                            btnLike.setVisibility(View.GONE);

                        }

                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

*/

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
