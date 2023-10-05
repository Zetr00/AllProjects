package com.example.petportal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class petAdapter extends RecyclerView.Adapter<petAdapter.CryptoViewHolder> {
    private ArrayList<Pet> pets;

    public petAdapter(ArrayList<Pet> pets){
        this.pets = pets;
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
        holder.bind(pets.get(position));
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class CryptoViewHolder extends RecyclerView.ViewHolder{

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
            });
        }

        public void bind(Pet pets){
            ApiUser apiUser;
            ImageView Iv = itemView.findViewById(R.id.photo);
            TextView Tv2 = itemView.findViewById(R.id.name);
            TextView Tv3 = itemView.findViewById(R.id.descriptions);
            TextView Tv4 = itemView.findViewById(R.id.breed);
            Picasso.get().load(pets.photo).into(Iv);
            Tv2.setText(pets.namePets);
            Tv3.setText(pets.descriptionPet);
            apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
            Call<Breed> getBreedId = apiUser.getBreedID(pets.breedId);
            getBreedId.enqueue(new Callback<Breed>() {
                @Override
                public void onResponse(Call<Breed> call, Response<Breed> response) {
                    if(response.isSuccessful()){
                        Breed breed = response.body();
                        Tv4.setText(breed.nameBreed);
                    }
                }

                @Override
                public void onFailure(Call<Breed> call, Throwable t) {

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, Description.class);
                    intent.putExtra("sc", pets);
                    context.startActivity(intent);
                }
            });

        }
    }
}

