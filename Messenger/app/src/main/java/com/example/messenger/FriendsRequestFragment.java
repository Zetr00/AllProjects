package com.example.messenger;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

public class FriendsRequestFragment extends Fragment {

    EditText NickName;

    Button AddFriend;

    TableLayout TableForData;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference friendRequestRef = db.collection("friendsRequest");

    private CollectionReference friendRef = db.collection("friends");

    private CollectionReference userRef = db.collection("users");

    boolean isNickName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_request, container, false);

        if (getContext().getResources() != null && getActivity().getResources() != null) {

            NickName = view.findViewById(R.id.NickName);

            AddFriend = view.findViewById(R.id.AddFriend);

            AddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userRef.whereEqualTo("nickname", NickName.getText().toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.get("nickname").toString().contains(NickName.getText().toString()) && !document.get("email").toString().contains(user.getEmail())) {
                                                isNickName = true;
                                                break;
                                            }
                                        }
                                        if (isNickName) {
                                            userRef.whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            String nickname = document.get("nickname").toString();
                                                            friendRequestRef.whereEqualTo("nameUserForRequest", NickName.getText().toString()).get()
                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            boolean isRequest = false;
                                                                            if (task.isSuccessful()) {
                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                    if (document.get("nameUserForRequest").toString().contains(NickName.getText().toString()) && document.get("nameUserRequest").toString().contains(nickname)) {
                                                                                        isRequest = true;
                                                                                        Toast.makeText(getActivity(), "Вы уже отправляли запрос дружбы этому пользователю", Toast.LENGTH_SHORT).show();
                                                                                        break;
                                                                                    }
                                                                                }

                                                                                if (!isRequest)
                                                                                    friendRequestRef.whereEqualTo("nameUserRequest", NickName.getText().toString()).get()
                                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    boolean isRequest = false;
                                                                                                    if (task.isSuccessful()) {
                                                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                            if (document.get("nameUserRequest").toString().contains(NickName.getText().toString()) && document.get("nameUserForRequest").toString().contains(nickname)) {
                                                                                                                isRequest = true;
                                                                                                                Toast.makeText(getActivity(), "Вы уже отправляли запрос дружбы этому пользователю", Toast.LENGTH_SHORT).show();
                                                                                                                break;
                                                                                                            }
                                                                                                        }

                                                                                                        if (!isRequest) {
                                                                                                            friendRef.whereEqualTo("friend", NickName.getText().toString()).get()
                                                                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                            boolean isFriend = false;
                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                                                    if (document.get("friend").toString().contains(NickName.getText().toString()) && document.get("friendAcceptRequest").toString().contains(nickname)) {
                                                                                                                                        isFriend = true;
                                                                                                                                        Toast.makeText(getActivity(), "Вы уже дружите с этим пользователем", Toast.LENGTH_SHORT).show();
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                if (!isFriend)
                                                                                                                                    friendRef.whereEqualTo("friend", NickName.getText().toString()).get()
                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                                                    boolean isFriend = false;
                                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                                                                            if (document.get("friendAcceptRequest").toString().contains(NickName.getText().toString()) && document.get("friend").toString().contains(nickname)) {
                                                                                                                                                                isFriend = true;
                                                                                                                                                                Toast.makeText(getActivity(), "Вы уже дружите с этим пользователем", Toast.LENGTH_SHORT).show();
                                                                                                                                                                break;
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        if (!isFriend) {
                                                                                                                                                            friendRef.whereEqualTo("friendAcceptRequest", NickName.getText().toString()).get()
                                                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                                                                            boolean isFriend = false;
                                                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                                                                                                    if (document.get("friendAcceptRequest").toString().contains(NickName.getText().toString()) && document.get("friend").toString().contains(nickname)) {
                                                                                                                                                                                        isFriend = true;
                                                                                                                                                                                        Toast.makeText(getActivity(), "Пользователь должен удалить вас из друзей перед тем как вы его добавите в друзья", Toast.LENGTH_SHORT).show();
                                                                                                                                                                                        break;
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                                if (!isFriend) {
                                                                                                                                                                                    FriendsRequest newRequest = new FriendsRequest(nickname, NickName.getText().toString());

                                                                                                                                                                                    friendRequestRef.add(newRequest).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                                                                                                                        @Override
                                                                                                                                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                                                                                                                                            Toast.makeText(getActivity(), "Вы отправили запрос дружбы", Toast.LENGTH_SHORT).show();
                                                                                                                                                                                        }
                                                                                                                                                                                    });
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                        }

                                                                                                    }
                                                                                                }
                                                                                            });
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

            TableForData = view.findViewById(R.id.TableForData);
            Completion();
        }
        return view;
    }

    String nickname;

    private void Completion() {
        userRef.whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    nickname = document.get("nickname").toString();
                }
                friendRequestRef.whereEqualTo("nameUserForRequest", nickname).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() > 0)
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            TableRow row = new TableRow(getActivity());
                                            TextView rowText = new TextView(getActivity());

                                            rowText.setTextSize(15);
                                            rowText.setTextColor(getResources().getColor(R.color.black));

                                            String data = document.get("nameUserRequest").toString();
                                            rowText.setText(data);
                                            row.addView(rowText);

                                            Button btnAdd = new Button(getActivity());
                                            btnAdd.setTag(document.getId());
                                            btnAdd.setText("Добавить");
                                            row.addView(btnAdd);
                                            btnAdd.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    Friends friend = new Friends(nickname, data);
                                                    friendRef.add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                        }
                                                    });

                                                    Friends friend2 = new Friends(data, nickname);
                                                    friendRef.add(friend2).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(getActivity(), "Вы приняли запрос дружбы", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                    friendRequestRef.document(btnAdd.getTag().toString()).delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    TableForData.removeAllViews();
                                                                    Completion();
                                                                }
                                                            });
                                                }
                                            });

                                            Button btnDelete = new Button(getActivity());
                                            btnDelete.setTag(document.getId().toString());
                                            btnDelete.setText("Отклонить");
                                            row.addView(btnDelete);
                                            btnDelete.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    friendRequestRef.document(btnAdd.getTag().toString()).delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(getActivity(), "Вы отклонили запрос", Toast.LENGTH_SHORT).show();
                                                                    TableForData.removeAllViews();
                                                                    Completion();
                                                                }
                                                            });
                                                }
                                            });

                                            TableForData.addView(row);
                                        }
                                    else {
                                        TableRow row = new TableRow(getActivity());
                                        TextView rowText = new TextView(getActivity());

                                        rowText.setTextSize(15);
                                        rowText.setTextColor(getResources().getColor(R.color.black));

                                        rowText.setText("У вас нет новых запросов");
                                        row.addView(rowText);

                                        TableForData.addView(row);
                                    }
                                }
                            }
                        });
            }
        });
    }
}