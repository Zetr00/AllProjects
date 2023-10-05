package com.example.crestnolik;

import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UploadGame();
    }

    GridLayout GL;
    boolean checkWin = false;

    private void UploadGame(){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        );
        GL = new GridLayout(this);
        GL.setColumnCount(3);
        setContentView(GL, params);

        for (int i = 0; i<9; i++){
            MaterialButton button = new MaterialButton(this);
            button.setText("");
            button.setOnClickListener(v -> Game(button));
            GL.addView(button);
        }
    }

    boolean who = true;
    private void Game(Button button){
        if (who & button.getText() == "" & !checkWin){
            button.setText("X");
            who = false;
        }
        if (!who & button.getText() == "" & !checkWin){
            button.setText("O");
            who = true;
        }
        if(!checkWin)
        {
            TestWinner();
        }
    }

    @SuppressLint("ResourceType")
    private void TestWinner(){
        String temp;
        for (int i = 0; i<2; i++) {
            if(i == 0) temp = "X";
            else temp = "O";

            List<Button> AllButtons = new ArrayList<Button>();
            for (int j = 0; j<9; j++)
            {
                AllButtons.add((Button)GL.getChildAt(j));
            }

            if(AllButtons.get(0).getText() == temp & AllButtons.get(1).getText() == temp & AllButtons.get(2).getText() == temp) checkWin = true;
            else
            if(AllButtons.get(3).getText() == temp & AllButtons.get(4).getText() == temp & AllButtons.get(5).getText() == temp) checkWin = true;
            else
            if(AllButtons.get(6).getText() == temp & AllButtons.get(7).getText() == temp & AllButtons.get(8).getText() == temp) checkWin = true;
            else

            if(AllButtons.get(0).getText() == temp & AllButtons.get(4).getText() == temp & AllButtons.get(8).getText() == temp) checkWin = true;
            else
            if(AllButtons.get(2).getText() == temp & AllButtons.get(4).getText() == temp & AllButtons.get(6).getText() == temp) checkWin = true;
            else

            if(AllButtons.get(0).getText() == temp & AllButtons.get(3).getText() == temp & AllButtons.get(6).getText() == temp) checkWin = true;
            else
            if(AllButtons.get(1).getText() == temp & AllButtons.get(4).getText() == temp & AllButtons.get(7).getText() == temp) checkWin = true;
            else
            if(AllButtons.get(2).getText() == temp & AllButtons.get(5).getText() == temp & AllButtons.get(8).getText() == temp) checkWin = true;

            if(checkWin){
                String stroke = "Победил " + temp;
                Toast.makeText(this, stroke, Toast.LENGTH_SHORT).show();
                i = 1;
            }
        }
    }
}