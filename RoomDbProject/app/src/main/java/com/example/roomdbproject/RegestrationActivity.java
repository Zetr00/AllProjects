package com.example.roomdbproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegestrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        Button button = findViewById(R.id.buttonGoto);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class );
            startActivity(intent);
        });

        Button newButton = findViewById(R.id.regestrat);
        newButton.setOnClickListener(view->{
            EditText login = findViewById(R.id.regLogin);
            EditText password = findViewById(R.id.regPassword);

            if(!login.getText().equals("") & !password.getText().equals("")){
                User user = new User(
                        login.getText().toString(),
                        password.getText().toString()
                );

                login.setText("");
                password.setText("");

                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                db.userDAO().insertUser(user);
                Toast.makeText(this, "Пользователь добавлен", Toast.LENGTH_LONG).show();
            }
        });
    }
}