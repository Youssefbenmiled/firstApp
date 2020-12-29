package com.example.firstapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.firstapp.model.Produit;

public class User implements Serializable {
    private String tel,email,username;
    private ArrayList<Produit> ProdList=new ArrayList<>();
    private ArrayList<Produit> PanierList=new ArrayList<>();


    public User(){

    }



    public User(String username,String email, String tel, ArrayList<Produit> prodList, ArrayList<Produit> panierList) {
        this.username=username;
        this.email=email;
        this.tel = tel;
        this.ProdList = prodList;
        this.PanierList = panierList;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    public String getTel() {
        return tel;
    }

    public void setTel(String telephone) {
        this.tel = telephone;
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

    @Override
    public String toString() {
        return "User{" +
                "tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", ProdList=" + ProdList +
                ", PanierList=" + PanierList +
                '}';
    }
}
