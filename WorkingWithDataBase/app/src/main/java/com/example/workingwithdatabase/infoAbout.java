package com.example.workingwithdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class infoAbout extends AppCompatActivity {
    Serial serial;
    String tempName;
    PaperDBClass paperDBSerial = new PaperDBClass();

    EditText nameSerial;
    EditText aboutSerial;
    EditText studentFIO;
    EditText studentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_about);

        nameSerial = findViewById(R.id.name);
        aboutSerial = findViewById(R.id.detail);
        studentFIO = findViewById(R.id.fio);
        studentScore = findViewById(R.id.score);

        Intent intent = getIntent();
        serial = (Serial) intent.getSerializableExtra("SERIAL");

        tempName = serial.getSerial_Name();
        nameSerial.setText(serial.getSerial_Name());
        aboutSerial.setText(serial.getSerial_Detail());
        studentFIO.setText(serial.getStudent_FIO());

        int score = serial.getStudent_Score();
        studentScore.setText(String.valueOf(score));
    }

    public void deleteSerial(View view){
        paperDBSerial.deleteSerial(serial);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void updateSerial(View view){
        try{
            int score = Integer.parseInt(studentScore.getText().toString());
            if(score>=1 & score<=10){
                Serial serialUpdate = new Serial(nameSerial.getText().toString(), aboutSerial.getText().toString(), studentFIO.getText().toString(), Integer.parseInt(studentScore.getText().toString()));
                paperDBSerial.updateSerial(serialUpdate, tempName);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Оценка сериала должна быть от 1 до 10", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ignored){
            Toast.makeText(this, "Ошибка изменения сериала", Toast.LENGTH_SHORT).show();
        }

    }
}
