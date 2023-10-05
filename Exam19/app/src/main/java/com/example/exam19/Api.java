package com.example.exam19;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("products")
    Call<ArrayList<Products>> getProducts();
}
