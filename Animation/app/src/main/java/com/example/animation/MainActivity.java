package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button animation1 = (Button) findViewById(R.id.button4);
        animation1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(animation1, "rotation",0f, 60f, 120f, 180f, 240f, 300f, 360f,0f);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation1, "translationY",0f, 1000f, 0f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(animation1, "translationX",0f, 460f,-480f, 0f);
                animator.setDuration(3000);
                animator.start();
                animator1.setDuration(3000);
                animator1.start();
                animator2.setDuration(3000);
                animator2.start();
            }
        });

        Button animation2 = (Button) findViewById(R.id.button2);
        animation2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(animation2, "scaleX",0f, 10f, 1f);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation2, "scaleY",0f, 10f, 1f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(animation2, "rotation",0f, 480f, -480f, 480f, -920f, 0f);
                animator.setDuration(5000);
                animator.start();
                animator1.setDuration(5000);
                animator1.start();
                animator2.setDuration(5000);
                animator2.start();
            }
        });

        Button animation3 = (Button) findViewById(R.id.button);
        animation3.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(animation3, "scaleX",0f, 20f, 1f);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation3, "scaleY",0f, 20f, 1f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(animation3, "rotationX",0f, 480f, -480f, 480f, -920f, 0f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(animation3, "rotationY",0f, 480f, -480f, 480f, -920f, 0f);
                animator.setDuration(5000);
                animator.start();
                animator1.setDuration(5000);
                animator1.start();
                animator2.setDuration(5000);
                animator2.start();
                animator3.setDuration(5000);
                animator3.start();
            }
        });

        Button animation4 = (Button) findViewById(R.id.button3);
        animation4.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(animation4, "pivotX",0f, 20f);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation4, "rotationX",0f, 480f, -480f, 480f, -920f, 0f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(animation4, "rotationY",0f, 480f, -480f, 480f, -920f, 0f);
                animator.setDuration(3000);
                animator.start();
                animator1.setDuration(3000);
                animator1.start();
                animator2.setDuration(3000);
                animator2.start();
            }
        });

        Button animation5 = (Button) findViewById(R.id.button5);
        animation5.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(animation5, "translationX",0f, 360f, -360f, 720f, -720f,1000f,-1000f, 0f);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation5, "translationY",0f, 360f, -360f, 720f, -720f,1000f,-1000f, 0f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(animation5, "rotation",0f, 480f, -480f, 920f, -920f, 0f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(animation5, "rotationX",0f, 480f, -480f, 920f, -920f, 0f);
                animator.setDuration(3000);
                animator.start();
                animator1.setDuration(3000);
                animator1.start();
                animator2.setDuration(3000);
                animator2.start();
                animator3.setDuration(3000);
                animator3.start();
            }
        });
    }
}