package com.example.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.deliver.database.Rating;
import com.example.deliver.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    public void TryReg(View view) {
        EditText name = findViewById(R.id.name_reg_view),
                login = findViewById(R.id.login_reg_view),
                password = findViewById(R.id.password_reg_view);
        String role = "User";
        if(name.getText().toString().length() > 10 &&
                login.getText().toString().length()>=7 &&
                password.getText().toString().length() > 8){
            User addUser = new User("",
                    name.getText().toString(),
                    login.getText().toString(),
                    password.getText().toString(),
                    role);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("User").add(addUser)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                String userID = task.getResult().getId();
                                Rating rating = new Rating("",5,"System","System rate");
                                db.collection("User").document(task.getResult().getId())
                                        .collection("Rate").add(rating).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Intent intent = new Intent(RegActivity.this, UserActivity.class);
                                                intent.putExtra("id",userID);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        }
                    });
        }
        else Toast.makeText(this, "Проверьте правильность данных", Toast.LENGTH_SHORT).show();

    }
}