package com.example.petportal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUser {
    @GET("Users")
    Call<ArrayList<User>> getUser();

    @POST("Users/")
    Call<User> addUser(@Body User user);

    @DELETE("Users/{id}")
    Call<User> deleteUser(@Path("id") int userId);

    @PUT("Users/{id}")
    Call<User> updateUser(@Path("id") int userId);

    @POST("Users/reg")
    Call<User> regUser(@Body User user);

    @POST("Users/auth")
    Call<User> authUser(@Body User user);

    @GET("Pets")
    Call<ArrayList<Pet>> getPetsList();

    @GET("Breeds/{id}")
    Call<Breed> getBreedID(@Path("id") int breedId);

    @GET("Reasons/{id}")
    Call<Reason> getReasonID(@Path("id") int reasonId);

    @GET("Status/{id}")
    Call<Status> getStatusID(@Path("id") int statusId);

    @GET("Types/{id}")
    Call<Type> getTypeID(@Path("id") int typeId);

    @POST("Pets/")
    Call<Pet> addPets(@Body Pet pets);

    @DELETE("Pets/{id}")
    Call<User> deletePets(@Path("id") int petsId);
}
