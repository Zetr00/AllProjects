package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Full_Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_description);
        TextView Tv = findViewById(R.id.textView);
        TextView Tv2 = findViewById(R.id.textView2);
        ImageView Iv = findViewById(R.id.imageView);

        Intent intent = getIntent();
        Crypto crypto = (Crypto) intent.getSerializableExtra("sc");

        Tv.setText(crypto.getName());
        Tv2.setText(crypto.getDescription());
        Iv.setImageResource(crypto.getPhoto());
    }
}