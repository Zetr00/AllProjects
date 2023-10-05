package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class RegActivity extends AppCompatActivity {

    private EditText EmailText, PasswordText, Surname, Name, NickName;
    private Button RegButton;

    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference userRef = db.collection("users");

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        firebaseAuth = FirebaseAuth.getInstance();

        EmailText = findViewById(R.id.EmailText);
        PasswordText = findViewById(R.id.PasswordText);
        Surname = findViewById(R.id.Surname);
        Name = findViewById(R.id.Name);
        NickName = findViewById(R.id.NickName);

        RegButton = findViewById(R.id.RegButton);

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validData();
            }
        });
    }

    private void validData(){

        if (!Patterns.EMAIL_ADDRESS.matcher(EmailText.getText().toString()).matches()){
            EmailText.setError("Неправильно введена почта");
        }
        else if (TextUtils.isEmpty(PasswordText.getText().toString())){
            PasswordText.setError("Пароль не может быть пустым");
        }
        else if (PasswordText.getText().toString().length() < 6){
            PasswordText.setError("Пароль должен содержать минимум 6 символов");
        }
        else if (NickName.getText().toString().length() < 6){
            NickName.setError("Никнейм не должен быть пустым");
        }
        else if (TextUtils.isEmpty(NickName.getText().toString())){
            NickName.setError("Никнейм должен содержать минимум 6 символов");
        }
        else if (TextUtils.isEmpty(Surname.getText().toString())){
            Surname.setError("Фамилия не должна быть пустая");
        }
        else if (TextUtils.isEmpty(Name.getText().toString())){
            Name.setError("Имя не должно быть пустое");
        }
        else{
            fireBaseRegistration();
        }
    }

    boolean isNickName = false;

    private void fireBaseRegistration() {
        userRef.whereEqualTo("nickname", NickName.getText().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("nickname").toString().contains(NickName.getText().toString())){
                                    isNickName = true;
                                    break;
                                }
                            }
                            if (!isNickName){
                                firebaseAuth.createUserWithEmailAndPassword(EmailText.getText().toString(), PasswordText.getText().toString())
                                        .addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (task.isSuccessful()) {
                                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                                    Users newUser = new Users(firebaseUser.getEmail(), firebaseUser.getUid(), Surname.getText().toString(), Name.getText().toString(), NickName.getText().toString());

                                                    userRef.add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(RegActivity.this, "Пользователь зарегистрирован.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(RegActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    Bitmap bitmap = ((BitmapDrawable) getDrawable(R.drawable.camera)).getBitmap();
                                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                    byte[] data = baos.toByteArray();

                                                    StorageReference storageRef = storage.getReferenceFromUrl("gs://messenger-a7506.appspot.com").child(NickName.getText().toString());

                                                    UploadTask uploadTask = storageRef.putBytes(data);
                                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(RegActivity.this, "Такой пользователь уже существует.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(RegActivity.this, "Такой пользователь существует", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegActivity.this, "Ошибка.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}