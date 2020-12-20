package com.example.firstapp;

import android.widget.ImageView;

import java.io.Serializable;

public class Produit implements Serializable {
    private String categorie;
    private String nomProduit;
    private ImageView img_produit;
    private String fournisseur;
    //private int nbPanier;


    public Produit(String categorie, String nomProduit,  String fournisseur) {
        this.categorie = categorie;
        this.nomProduit = nomProduit;
        //this.nbPanier=0;
        //this.img_produit = img_produit;
        this.fournisseur = fournisseur;
    }
    public Produit(){

    }

   /*public int getNbPanier() {
        return nbPanier;
    }

    public void setNbPanier(int nbPanier) {
        this.nbPanier = nbPanier;
    }
*/
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }





   /* public ImageView getImg_produit() {
        return img_produit;
    }

    public void setImg_produit(ImageView img_produit) {
        this.img_produit = img_produit;
    }*/

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }
/*
    public void plusPanier()
    {
         this.nbPanier++;
    }

    public void moinsPanier()
    {
        if(this.nbPanier>0)
         this.nbPanier--;


    }*/
}
