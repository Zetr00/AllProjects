package com.example.workingwithdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    RecyclerView serial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serial = (RecyclerView) findViewById(R.id.recyclerView);
        Button goAddSerial = (Button) findViewById(R.id.addButton);

        Paper.init(this);
        PaperDBClass paperDBClass = new PaperDBClass();

        serial.setLayoutManager(new LinearLayoutManager(this));
        serial.setHasFixedSize(true);
        SerialAdapter adapter = new SerialAdapter(this, paperDBClass.getSerial());
        serial.setAdapter(adapter);

        goAddSerial.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), addSerial.class);
            startActivity(intent);
        });
    }
}