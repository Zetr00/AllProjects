package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import kotlin.collections.ArraysKt;

public class ChatActivity extends AppCompatActivity {

    public static String NameChat;
    public static String NickName;
    public static String NickName2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Ref = database.getReference(NameChat);

    Button send, Back;

    EditText messageh;

    RecyclerView recyclerView;

    ArrayList<Messages> messageView = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference friendRef = db.collection("friends");

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        friendRef.whereEqualTo("friendAcceptRequest", NickName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean isTrue = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("friendAcceptRequest").toString().contains(NickName) && document.get("friend").toString().contains(NickName2)) {
                                isTrue = true;
                                break;
                            }
                        }

                        if (!isTrue) {
                            send.setVisibility(View.GONE);
                            messageh.setVisibility(View.GONE);
                            Toast.makeText(ChatActivity.this, "Вы не можете писать пользователям, которых нет у вас в друзьях", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        messageh = findViewById(R.id.messageh);

        recyclerView = findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MessageAdapter adapter = new MessageAdapter(getApplicationContext(), messageView);

        recyclerView.setAdapter(adapter);

        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageh.getText().toString().length() == 0)
                    Toast.makeText(ChatActivity.this, "Введите сообщение", Toast.LENGTH_SHORT).show();
                else {
                    Ref.push().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String key = snapshot.getKey();
                            Ref.child(key).child("nickname").setValue(NickName);
                            Ref.child(key).child("msg").setValue(messageh.getText().toString());
                            Ref.child(key).child("isDeleted").setValue(false);

                            if (snapshot.hasChildren()){
                                i++;
                                if (i == 3) {
                                    Messages messages = new Messages(snapshot.child("nickname").getValue(String.class) + ":", snapshot.child("msg").getValue(String.class));
                                    messageView.add(messages);
                                    adapter.notifyDataSetChanged();
                                    recyclerView.smoothScrollToPosition(messageView.size());
                                    i = 0;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        Back = findViewById(R.id.Back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (i != 1) {
                    Messages messages = new Messages(snapshot.child("nickname").getValue(String.class) + ":", snapshot.child("msg").getValue(String.class));
                    messageView.add(messages);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(messageView.size());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}