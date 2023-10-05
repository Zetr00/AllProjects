package com.example.petportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authorization extends AppCompatActivity {
    ApiUser apiUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        EditText Et = findViewById(R.id.editTextTextPersonName);
        EditText Et2 = findViewById(R.id.editTextTextPersonName2);
        Button Bt = findViewById(R.id.button);

        Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User users = new User();
                users.login = Et.getText().toString().trim();
                users.password = Et2.getText().toString().trim();
                users.fioUser = "string";
                users.dateOfBirth = "2000-01-01";
                users.email = "weufhwieuhf@mail.ru";
                users.phoneNumber = "7(999)999-00-00";
                users.salt = "string";
                apiUser = RequestBuilder.buildRequest().create(ApiUser.class);
                Call<User> getUserList = apiUser.authUser(users);
                getUserList.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User users = response.body();
                        if(users != null){
                            Toast.makeText(getApplicationContext(), "Успешная авторизация!", Toast.LENGTH_SHORT).show();
                            Context context = Et.getContext();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("sc", users);
                            context.startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }
}
