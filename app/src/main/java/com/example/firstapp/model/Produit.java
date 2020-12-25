package com.example.firstapp.model;

import android.widget.ImageView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Produit implements Serializable {


    private String fournisseur;
    private int nbPanier;
    private String currentTime;
    private Boolean disponible;
    private String dateDisponibilite;


    public Produit(String adresse,Boolean disponible,String datdispo) {

        this.fournisseur = adresse;
        this.disponible=disponible;
        if(disponible)
        {
            //String currentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date());

            this.currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            this.dateDisponibilite=null;


        }
        else
        {
            this.currentTime = null;
            this.dateDisponibilite=datdispo;


        }
        this.nbPanier=0;


        //this.currentTime = Calendar.getInstance().getTime();

    }
    public Produit(){

    }

    public String getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(String dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
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

    /*public String getCategorie() {
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



*/


    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }


    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


}
