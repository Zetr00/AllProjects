package com.example.exam19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.CryptoViewHolder>{
    private ArrayList<Products> products;

    public productsAdapter(ArrayList<Products> products){
        this.products = products;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class CryptoViewHolder extends RecyclerView.ViewHolder{

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
            });
        }

        public void bind(Products products){
            ImageView Iv = itemView.findViewById(R.id.imageView);
            TextView Tv2 = itemView.findViewById(R.id.titleTextView);
            TextView Tv3 = itemView.findViewById(R.id.descriptionTextView);
            TextView Tv4 = itemView.findViewById(R.id.priceTextView);
            TextView Tv5 = itemView.findViewById(R.id.discountPercentageTextView);
            TextView Tv6 = itemView.findViewById(R.id.ratingTextView);
            TextView Tv7 = itemView.findViewById(R.id.stockTextView);
            TextView Tv8 = itemView.findViewById(R.id.brandTextView);
            TextView Tv9 = itemView.findViewById(R.id.categoryTextView);
            //Picasso.get().load(products.images.get(1)).into(Iv);
            Tv2.setText(products.title);
            Tv3.setText(products.description);
            Tv4.setText(products.price);
            Tv5.setText(products.discountPercentage);
            Tv6.setText(products.rating);
            Tv7.setText(products.stock);
            Tv8.setText(products.brand);
            Tv5.setText(products.discountPercentage);

        }
    }
}
