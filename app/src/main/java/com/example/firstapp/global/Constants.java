package com.example.firstapp.global;

import com.example.firstapp.model.Produit;

import java.util.ArrayList;

public class Constants {



    /* public Bundle sendUser(User us){

        Bundle args = new Bundle();

        if(us!=null)
        {
            args.putSerializable("USER",(Serializable)us);
        }
        return args;
    }*/

    //data1=getResources().getStringArray(R.array.programming_languages);
    //data2=getResources().getStringArray(R.array.description);

      /*private void makePanier(String Uid){



        FirebaseDatabase.getInstance().getReference("Users")
                .child(Uid)
                .child("panier")
                .setValue(new Produit())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void makeProducts(String ch) {
        FirebaseDatabase.getInstance().getReference("Users")
                .child(ch)
                .child("produits")
                .setValue(new Produit())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }*/
 /*Intent intent=new Intent(getApplicationContext(),LoginActivity.class);

                user us=new user(pass1,phone,email,null,null);
                Bundle args = new Bundle();

                if(us!=null)
                {
                    Users.add(us);
                    args.putSerializable("ARRAYLIST",(Serializable)Users);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                }
*/

        /*Intent intent = getIntent();
        if(intent.getBundleExtra("BUNDLE")!=null)
        {
            Bundle args = intent.getBundleExtra("BUNDLE");

            Users=((ArrayList<user>)args.getSerializable("ARRAYLIST"));

        }*/

    /*Intent intent = getIntent();
        if(intent.getBundleExtra("BUNDLE")!=null)
        {
            Bundle args = intent.getBundleExtra("BUNDLE");

            Users=((ArrayList<user>)args.getSerializable("ARRAYLIST"));
        }*/

    /*user=TrouveUser(username,password);

                if(user!=null)
                {
                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);

                    intent.putExtra("BUNDLEUSER",sendUser(user));
                    startActivity(intent);
                        finish();


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Utilisateur introuvable",Toast.LENGTH_LONG).show();

                }


*/

    //Bundle args = new Bundle();


    //args.putSerializable("ARRAYLIST",(Serializable)Users);
    //intent.putExtra("BUNDLE",args);

     /*public String getCode(){
        String ch="0123456789";
        String res="";

        for(int i=0;i<6;i++){
           int random=new Random().nextInt(ch.length());
            res+=ch.charAt(random);

        }


        return res;

    }*/

                    /*if(utilisateur.isEmailVerified())
                    {
                        redirectUser(email);

                    }
                    else
                    {
                        utilisateur.sendEmailVerification();
                        Toast.makeText(getApplicationContext(),"Check your email to verify your account please !",Toast.LENGTH_LONG).show();

                    }*/
      /*User user=null;
        Intent intent = getIntent();

        if(intent.getBundleExtra("BUNDLEUSER")!=null)
        {
            Bundle args = intent.getBundleExtra("BUNDLEUSER");
             user=(User)args.getSerializable("USER");


        }
        return user;*/
}
