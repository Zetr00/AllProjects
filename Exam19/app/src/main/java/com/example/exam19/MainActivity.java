package com.example.exam19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView serialRecycler;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serialRecycler = findViewById(R.id.recycler_view);

        api = RequestBuilder.buildRequest().create(Api.class);
        Call<ArrayList<Products>> getProducts = api.getProducts();

        getProducts.enqueue(new Callback<ArrayList<Products>>() {
            @Override
            public void onResponse(Call<ArrayList<Products>> call, Response<ArrayList<Products>> response) {
                if(response.isSuccessful()){
                    serialRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    serialRecycler.setHasFixedSize(true);

                    ArrayList<Products> listProducts = response.body();
                    serialRecycler.setAdapter(new productsAdapter(listProducts));
                }else{
                    Toast.makeText(getApplicationContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Products>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}