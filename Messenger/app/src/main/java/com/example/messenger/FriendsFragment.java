package com.example.messenger;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FriendsFragment extends Fragment {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference friendRef = db.collection("friends");

    private CollectionReference userRef = db.collection("users");

    private CollectionReference dialRef = db.collection("dialog");

    FirebaseStorage storage = FirebaseStorage.getInstance();

    LinearLayout Friend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Completion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        if (getContext().getResources() != null && getActivity().getResources() != null) {
            Friend = view.findViewById(R.id.Friend);
        }
        return view;
    }

    private String nickname;
    private String documentFriend;

    private void Completion() {
        userRef.whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    nickname = document.get("nickname").toString();
                    friendRef.whereEqualTo("friendAcceptRequest", nickname).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() > 0)
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            StorageReference storageRef = storage.getReferenceFromUrl("gs://messenger-a7506.appspot.com").child(nickname);
                                            storageRef.getMetadata().addOnCompleteListener(new OnCompleteListener<StorageMetadata>() {
                                                @Override
                                                public void onComplete(@NonNull Task<StorageMetadata> task) {
                                                    if (task.isSuccessful()) {
                                                        TextView textView = new TextView(getActivity().getApplicationContext());
                                                        textView.setText(document.get("friend").toString());
                                                        documentFriend = document.get("friend").toString();
                                                        textView.setTextColor(getResources().getColor(R.color.black));
                                                        textView.setTextSize(20);
                                                        textView.setGravity(Gravity.CENTER);
                                                        Friend.addView(textView);

                                                        ImageView img = new ImageView(getActivity());
                                                        img.setMaxHeight(1000);
                                                        img.setMinimumHeight(1000);
                                                        String url = "https://firebasestorage.googleapis.com/v0/b/messenger-a7506.appspot.com/o/" + document.get("friend").toString() + "?alt=media&token=3f3efddc-cf60-4e2f-980f-c10ba35b3e4d";
                                                        Glide.with(getContext()).load(url).into(img);
                                                        Friend.addView(img);

                                                        Button btnAdd = new Button(getActivity());
                                                        btnAdd.setTag(nickname + " " + documentFriend);
                                                        btnAdd.setText("Создать чат");
                                                        Friend.addView(btnAdd);
                                                        btnAdd.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                dialRef.whereEqualTo("nameDialog", btnAdd.getTag().toString()).get()
                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                boolean isTrue = false;
                                                                                if (task.isSuccessful())
                                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                        if (document.get("nameDialog").toString().contains(btnAdd.getTag().toString())) {
                                                                                            isTrue = true;
                                                                                            break;
                                                                                        }
                                                                                    }
                                                                                if (isTrue) {
                                                                                    String[] words = btnAdd.getTag().toString().split(" ");
                                                                                    ChatActivity.NameChat = btnAdd.getTag().toString();
                                                                                    ChatActivity.NickName = words[0];
                                                                                    ChatActivity.NickName2 = words[1];
                                                                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                                                                    startActivity(intent);
                                                                                    getActivity().finish();
                                                                                } else {
                                                                                    dialRef.whereEqualTo("nameDialog", GetName(btnAdd.getTag().toString())).get()
                                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    boolean isTrue = false;
                                                                                                    if (task.isSuccessful()) {
                                                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                            if (document.get("nameDialog").toString().contains(GetName(btnAdd.getTag().toString()))) {
                                                                                                                isTrue = true;
                                                                                                                break;
                                                                                                            }
                                                                                                        }

                                                                                                        if (isTrue) {
                                                                                                            ChatActivity.NameChat = GetName(btnAdd.getTag().toString());
                                                                                                            String[] words = ChatActivity.NameChat.split(" ");
                                                                                                            ChatActivity.NickName = words[0];
                                                                                                            ChatActivity.NickName2 = words[1];
                                                                                                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                                                                                                            startActivity(intent);
                                                                                                            getActivity().finish();
                                                                                                        } else {
                                                                                                            String[] words = btnAdd.getTag().toString().split(" ");
                                                                                                            dialog Dial = new dialog(btnAdd.getTag().toString(), words[0], words[1]);

                                                                                                            dialRef.add(Dial).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                                                    String[] words = btnAdd.getTag().toString().split(" ");
                                                                                                                    ChatActivity.NameChat = btnAdd.getTag().toString();
                                                                                                                    ChatActivity.NickName = words[0];
                                                                                                                    ChatActivity.NickName2 = words[1];
                                                                                                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                                                                                                    startActivity(intent);
                                                                                                                    getActivity().finish();
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
                                                        });

                                                        Button btnDelete = new Button(getActivity());
                                                        btnDelete.setTag(document.getId());
                                                        btnDelete.setText("Удалить из друзей");
                                                        Friend.addView(btnDelete);
                                                        btnDelete.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                friendRef.document(btnDelete.getTag().toString()).delete()
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Toast.makeText(getActivity(), "Вы удалили друга", Toast.LENGTH_SHORT).show();
                                                                                Friend.removeAllViews();
                                                                                Completion();
                                                                            }
                                                                        });
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    else {
                                        TextView rowText = new TextView(getActivity());
                                        rowText.setTextColor(getResources().getColor(R.color.black));
                                        rowText.setTextSize(20);
                                        rowText.setGravity(Gravity.CENTER);
                                        rowText.setText("У вас нет друзей(");
                                        Friend.addView(rowText);
                                    }
                                }
                            });
                }
            }
        });
    }

    private String GetName(String welcome){
        final List< String > words = Arrays.asList( welcome.split( "\\s" ));
        Collections.reverse( words );
        final String rev = words.stream().collect( Collectors.joining( " " ));

        return rev;
    }
}