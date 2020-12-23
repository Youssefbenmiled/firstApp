package com.example.firstapp.model;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Produit{
    private String categorie;
    private String nomProduit;
    private String fournisseur;
    private int nbPanier;
    private Date currentTime;
    private Boolean disponible;


    public Produit(String categorie, String nomProduit,String fournisseur,Boolean disponible) {
        this.categorie = categorie;
        this.nomProduit = nomProduit;
        this.nbPanier=0;
        this.fournisseur = fournisseur;
        this.disponible=disponible;
        this.currentTime = null;

        //this.currentTime = Calendar.getInstance().getTime();

    }
    public Produit(){

    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public int getNbPanier() {
        return nbPanier;
    }

    public void setNbPanier(int nbPanier) {
        this.nbPanier = nbPanier;
    }

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






    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
