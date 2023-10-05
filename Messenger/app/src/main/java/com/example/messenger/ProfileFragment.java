package com.example.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment {

    Button Exit, AddPhoto, Change;

    ImageView img;

    EditText Surname, Name;

    int requestcode = 1;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference userRef = db.collection("users");

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (getContext().getResources() != null && getActivity().getResources() != null) {
            Surname = view.findViewById(R.id.Surname);
            Name = view.findViewById(R.id.Name);

            img = view.findViewById(R.id.img);

            AddPhoto = view.findViewById(R.id.AddPhoto);

            AddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/**");
                    startActivityForResult(intent, requestcode);
                }
            });

            Change = view.findViewById(R.id.Change);

            Change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Surname.getText().toString().length() == 0) {
                        Surname.setError("Фамилия не может быть пустой");
                    } else if (Name.getText().toString().length() == 0) {
                        Name.setError("Имя не может быть пустым");
                    } else {

                        userRef.document(idDoc).update("name", Name.getText().toString(),
                                        "surname", Surname.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "Данные о вас изменены", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        StorageReference storageRef = storage.getReferenceFromUrl("gs://messenger-a7506.appspot.com").child(nickname);

                        UploadTask uploadTask = storageRef.putBytes(data);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            }
                        });
                    }
                }
            });

            Exit = view.findViewById(R.id.Exit);
            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            Completion();
        }
        return view;
    }

    String idDoc;

    String nickname;

    private void Completion(){
        userRef.whereEqualTo("email", user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            nickname = document.get("nickname").toString();
                            Surname.setText(document.get("surname").toString());
                            Name.setText(document.get("name").toString());
                            idDoc = document.getId();

                            String url = "https://firebasestorage.googleapis.com/v0/b/messenger-a7506.appspot.com/o/" + nickname + "?alt=media&token=3f3efddc-cf60-4e2f-980f-c10ba35b3e4d";
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://messenger-a7506.appspot.com").child(nickname);
                            storageRef.getMetadata().addOnCompleteListener(new OnCompleteListener<StorageMetadata>() {
                                @Override
                                public void onComplete(@NonNull Task<StorageMetadata> task) {
                                    if (task.isSuccessful())
                                        Glide.with(getContext()).load(url).into(img);
                                }
                            });
                        }
                    }
                });
    }

    Uri uri;

    public void onActivityResult(int requestcode, int resultCode, Intent data){
        super.onActivityResult(resultCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (data == null){
                return;
            }

            uri = data.getData();
            img.setImageURI(uri);
        }
    }
}