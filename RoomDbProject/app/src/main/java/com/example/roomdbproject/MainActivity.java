package com.example.roomdbproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        int idUser = settings.getInt("idUser", -1);
        String log = settings.getString("login", "notFound");
        String pass = settings.getString("password", "notFound");
        if(idUser != -1 & !log.equals("notFound") & !pass.equals("notFound"))
        {
            Intent intent = new Intent(this, GlobalActivity.class);
            User user = new User(log, pass);
            user.setId(idUser);
            intent.putExtra("data", user);
            startActivity(intent);
            finish();
        }


        allUsers = findViewById(R.id.allUsers);
        fetchUsers();

        Button button = findViewById(R.id.goToRegestration);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegestrationActivity.class);
            startActivity(intent);
        });

        Button newButton = findViewById(R.id.avtoriz);
        newButton.setOnClickListener(view->{
            EditText login = findViewById(R.id.etLogin);
            EditText password = findViewById(R.id.etPassword);

            if(!login.getText().equals("") & !password.getText().equals("")){
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                List<User> users = db.userDAO().getAllUsers();

                boolean auth = false;

                for(int i = 0; i<users.size(); i++){
                    if(login.getText().toString().equals(users.get(i).getLogin()) &
                    password.getText().toString().equals(users.get(i).getPassword())){
                        auth = true;
                        saveUserLogin(users.get(i));

                        Intent intent = new Intent(this, GlobalActivity.class);
                        User user = new User(users.get(i).getLogin(), users.get(i).getPassword());
                        user.setId(users.get(i).getId());
                        intent.putExtra("data", user);
                        startActivity(intent);
                        finish();
                    }
                }
                if(!auth) Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchUsers(){
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<User> users = db.userDAO().getAllUsers();
        for(int i = 0; i<users.size(); i++){
            allUsers.append("Id: " + users.get(i).getId()+"; Login: " + users.get(i).getLogin() + "; Password: " + users.get(i).getPassword() + "\n");
        }
    }

    private void saveUserLogin(User user){
        SharedPreferences settings = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("idUser", user.getId());
        editor.putString("login", user.getLogin());
        editor.putString("password", user.getPassword());
        editor.apply();
    }
}