package com.example.knopka;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "Rotation", 0f, 270f, 180f, 360f);
            animator.setRepeatMode();
            animator.setDuration(5000);
            animator.start();
        });
    }
}