package com.example.workingwithdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addSerial extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serial);

        EditText name = findViewById(R.id.nameSerial);
        EditText detail = findViewById(R.id.detailSerial);
        EditText fio = findViewById(R.id.fioSerial);
        EditText scoreText = findViewById(R.id.scoreSerial);
        Button addSerial = findViewById(R.id.addSerialMain);
        PaperDBClass paperDBClass = new PaperDBClass();

        addSerial.setOnClickListener(view -> {
            try{
                int score = Integer.parseInt(scoreText.getText().toString());
                if(score >= 1 & score <=10){
                    Serial serial = new Serial(name.getText().toString(), detail.getText().toString(), fio.getText().toString(), score);
                    paperDBClass.addSerial(serial, this);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Оценка сериала должна быть от 1 до 10", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ignored){
                Toast.makeText(this, "Ошибка добавления сериала", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
