package com.example.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliver.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void RegPage(View view) {
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
        finish();
    }

    public void EnterClick(View view) {
        EditText loginText = findViewById(R.id.login_text),
                passwordTest = findViewById(R.id.password_text);
        users.whereEqualTo("login", loginText.getText().toString())
                .whereEqualTo("password", passwordTest.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().getDocuments().size()>0){
                                User user = task.getResult().getDocuments().get(0).toObject(User.class);
                                user.setId(task.getResult().getDocuments().get(0).getId());
                                Intent intent;
                                intent = new Intent(MainActivity.this, UserActivity.class);
                                intent.putExtra("id", user.getId());
                                startActivity(intent);
                            }
                            else Toast.makeText(MainActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}