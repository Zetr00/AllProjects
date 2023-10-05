package com.example.firebaseproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authorization  extends AppCompatActivity {

    private EditText emailEditText,passwordEditText;
    private Button loginButton;
    private TextView goToReg;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        EditText emailEditText = findViewById(R.id.email_edit_text1);
        EditText passwordEditText = findViewById(R.id.password_edit_text1);
        Button loginButton= findViewById(R.id.login_button1);
        TextView goToReg= findViewById(R.id.goToReg);
        firebaseAuth = FirebaseAuth.getInstance();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                firebaseAutorization(email, password);
            }
        });

        goToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Authorization.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void firebaseAutorization(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        SharedPreferences settings = Authorization.this.getSharedPreferences("PreferencesName", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("emailUser", email);
                        editor.putString("passwordUser", password);
                        editor.apply();
                        Toast.makeText(Authorization.this, "Успешная авторизация", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Authorization.this, "Неверные данные", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
