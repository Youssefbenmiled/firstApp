package com.example.firstapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.firstapp.model.Produit;

public class User {
    String Tel,email;
    ArrayList<Produit> ProdList=new ArrayList<>();
    ArrayList<Produit> PanierList=new ArrayList<>();


    public User(){

    }



    public User(String email, String tel, ArrayList<Produit> prodList, ArrayList<Produit> panierList) {
        this.email=email;
        this.Tel = tel;

        this.ProdList = prodList;
        this.PanierList = panierList;


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public ArrayList<Produit> getProdList() {
        return ProdList;
    }

    public void setProdList(ArrayList<Produit> prodList) {
        ProdList = prodList;
    }

    public ArrayList<Produit> getPanier() {
        return PanierList;
    }

    public void setPanier(ArrayList<Produit> panierList) {
        PanierList = panierList;
    }

    public void addTopanier(Produit p){
        if(!this.ProdList.contains(p))
        {
            this.PanierList.add(p);

        }
    }

   /* public void DeleteFromPanier(produit p)
    {
        this.PanierList.remove(p);
    }

    public void addToProds(produit p){

            this.ProdList.add(p);
    }

    public void DeleteFromProds(produit p)
    {
        this.ProdList.remove(p);
    }

    public void UpdateProds(produit p,int index)
    {
        this.ProdList.add(index,p);
    }


*/

}
