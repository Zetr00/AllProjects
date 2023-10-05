package com.example.petportal;

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

    RecyclerView serialRecycler;
    ApiUser apiUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadInRecycler();
    }
    private void uploadInRecycler(){
        serialRecycler = findViewById(R.id.rV1);

        apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
        Call<ArrayList<Pet>> getPetsList = apiUser.getPetsList();

        getPetsList.enqueue(new Callback<ArrayList<Pet>>() {
            @Override
            public void onResponse(Call<ArrayList<Pet>> call, Response<ArrayList<Pet>> response) {
                if(response.isSuccessful()){
                    serialRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    serialRecycler.setHasFixedSize(true);

                    ArrayList<Pet> listPets = response.body();
                    serialRecycler.setAdapter(new petAdapter(listPets));
                }else{
                    Toast.makeText(getApplicationContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pet>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}