package com.example.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context ct;
    ArrayList<Produit> produits;
    int images[];

    public Adapter(Context context, ArrayList<Produit> data1, int imgs[]){
        this.ct=context;
        this.produits=data1;

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
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {


        final Produit unproduit=produits.get(position);
        //holder.iv.setImageResource(images[0]);
        holder.categorie.setText(unproduit.getCategorie());
        holder.nom.setText(unproduit.getNomProduit());
        holder.fournisseur.setText(unproduit.getFournisseur());
        //holder.nbpanier.setText(String.valueOf(unproduit.getNbPanier()));
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        }



    }
}
