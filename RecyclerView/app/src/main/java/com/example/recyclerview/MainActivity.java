package com.example.recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.list);

        ArrayList<Crypto> cryptos = new ArrayList<>();
        cryptos.add(new Crypto("Биткоин", "Пиринговая платёжная система, использующая одноимённую единицу для учёта операций.", R.drawable.btc));
        cryptos.add(new Crypto("Эфириум", "Криптовалюта и платформа для создания децентрализованных онлайн-сервисов на базе блокчейна, работающих на базе умных контрактов.", R.drawable.eth));
        cryptos.add(new Crypto("Солана", "Публичная блокчейн-платформа с функциональностью смарт-контрактов. Ее родной криптовалютой является SOL.", R.drawable.sol));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new CryptoAdapter(cryptos));
    }}