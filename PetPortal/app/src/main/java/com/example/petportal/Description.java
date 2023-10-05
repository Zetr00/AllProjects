package com.example.petportal;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Description extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApiUser apiUser;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        TextView Tv = findViewById(R.id.textView4);
        ImageView Iv = findViewById(R.id.imageView);
        TextView Tv1 = findViewById(R.id.textView5);
        TextView Tv2 = findViewById(R.id.textView6);
        TextView Tv3 = findViewById(R.id.textView7);
        TextView Tv4 = findViewById(R.id.textView8);
        TextView Tv5 = findViewById(R.id.textView9);
        TextView Tv6 = findViewById(R.id.textView10);
        TextView Tv7 = findViewById(R.id.textView11);
        TextView Tv8 = findViewById(R.id.textView12);
        TextView Tv9 = findViewById(R.id.textView13);
        TextView Tv10 = findViewById(R.id.textView14);


        Intent intent = getIntent();
        Pet pets = (Pet) intent.getSerializableExtra("sc");

        Tv.setText(pets.namePets);
        Tv1.setText(pets.dateOfBirth);
        Tv2.setText(pets.color);
        Tv3.setText(pets.gender);
        Picasso.get().load(pets.photo).into(Iv);
        Tv4.setText(String.valueOf(pets.weightPet));
        Tv5.setText(String.valueOf(pets.heightPet));
        Tv6.setText(pets.descriptionPet);
        apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
        Call<Breed> getBreedId = apiUser.getBreedID(pets.breedId);
        getBreedId.enqueue(new Callback<Breed>() {
            @Override
            public void onResponse(Call<Breed> call, Response<Breed> response) {
                if(response.isSuccessful()){
                    Breed breed = response.body();
                    Tv7.setText(breed.nameBreed);
                }
            }

            @Override
            public void onFailure(Call<Breed> call, Throwable t) {

            }
        });
        apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
        Call<Reason> getReasonId = apiUser.getReasonID(pets.reasonId);
        getReasonId.enqueue(new Callback<Reason>() {
            @Override
            public void onResponse(Call<Reason> call, Response<Reason> response) {
                if(response.isSuccessful()){
                    Reason reason = response.body();
                    Tv8.setText(reason.nameReason);
                }
            }

            @Override
            public void onFailure(Call<Reason> call, Throwable t) {

            }
        });
        apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
        Call<Status> getStatusId = apiUser.getStatusID(pets.statusId);
        getStatusId.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.isSuccessful()){
                    Status status = response.body();
                    Tv9.setText(status.nameStatus);
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
        apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
        Call<Type> getTypeId = apiUser.getTypeID(pets.typeId);
        getTypeId.enqueue(new Callback<Type>() {
            @Override
            public void onResponse(Call<Type> call, Response<Type> response) {
                if(response.isSuccessful()){
                    Type type = response.body();
                    Tv10.setText(type.nameType);
                }
            }

            @Override
            public void onFailure(Call<Type> call, Throwable t) {

            }
        });
    }
}
