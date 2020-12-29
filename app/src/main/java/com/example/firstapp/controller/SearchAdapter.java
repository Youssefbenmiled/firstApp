package com.example.firstapp.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.model.Upload;
import com.example.firstapp.model.User;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<User> users;
    private DatabaseReference mDatabaseRef;

    public SearchAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
        SearchAdapter.MyViewHolder usersViewHolder=new SearchAdapter.MyViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        final User user=users.get(position);
        holder.tv_user.setText(user.getUsername());
        Log.d("USER",user.toString());
        holder.tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ProfilActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("USER",(Serializable)user);
                intent.putExtra("BUNDLE",b);
                //intent.putExtra("from","searchadapter");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user=itemView.findViewById(R.id.tv_username);
        }


    }



}
