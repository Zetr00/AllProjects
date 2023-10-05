package com.example.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliver.Adapters.MessageAdapter;
import com.example.deliver.Adapters.RatingAdapter;
import com.example.deliver.database.Rating;
import com.example.deliver.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Profile extends AppCompatActivity {

    private String curUserID;
    private String profileUserID;
    private User profileUser;
    private User currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        curUserID = getIntent().getStringExtra("userID");
        profileUserID = getIntent().getStringExtra("profileID");
        db.collection("User").document(profileUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().toObject(User.class);
                    if(user == null){
                        Toast.makeText(Profile.this, "Профиль не найден", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    profileUser = user;
                    ReloadUI();
                } else {
                    Toast.makeText(Profile.this, "Профиль не найден", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        db.collection("User").document(curUserID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        currentUser = task.getResult().toObject(User.class);
                    }
                });
    }

    private void ReloadUI(){
        TextView name = findViewById(R.id.profile_name),
                rating = findViewById(R.id.profile_rating);
        name.setText(profileUser.getName());
        db.collection("User").document(profileUserID).collection("Rate")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Rating> ratings = task.getResult().toObjects(Rating.class);
                            ReloadRec(ratings);
                            double totalRating = 0;
                            for (Rating rate:
                                 ratings) {
                                totalRating += rate.getRate();
                            }
                            totalRating /= ratings.size();
                            totalRating = Math.round(totalRating * 100);
                            totalRating /= 100;
                            rating.setText("Рейтинг: "+totalRating+" из 5.0");
                        }
                    }
                });
    }
    private void ReloadRec(List<Rating> ratings){
        RecyclerView rec = findViewById(R.id.rating_rec);
        rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rec.setHasFixedSize(true);

        RatingAdapter adapter = new RatingAdapter(ratings);
        rec.setAdapter(adapter);
    }

    public void SendRate(View view) {
        EditText rate = findViewById(R.id.profile_rating_edit),
                message = findViewById(R.id.profile_message_edit);
        String rateText = rate.getText().toString(),
                messageText = message.getText().toString();
        int rateNum = Integer.parseInt(rateText);
        rateNum = rateNum > 5? 5: (rateNum < 1)? 1 : rateNum;
        Rating rating = new Rating("",rateNum,currentUser.getName(),messageText);
        db.collection("User").document(profileUserID).collection("Rate")
                .add(rating)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        rate.setText("");
                        message.setText("");
                        ReloadUI();
                        Toast.makeText(Profile.this, "Отзыв отправлен", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}