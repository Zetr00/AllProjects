package com.example.menufragmentsnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menufragmentsnavigation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottonNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.First:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Вы нажали на цифорку один", Toast.LENGTH_LONG);
                    toast.show();
                    break;
                case R.id.Second:
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Вы нажали на цифорку два", Toast.LENGTH_LONG);
                    toast1.show();
                    break;
                case R.id.Third:
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Вы нажали на цифорку три", Toast.LENGTH_LONG);
                    toast2.show();
                    break;
            }

            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int idItem = item.getItemId();
        switch (idItem){
            case R.id.Option:
                setFragment(new FirstFragment());
                break;
            case R.id.About:
                setFragment(new SecondFragment());
                break;
            case R.id.Author:
                setFragment(new ThirdFragment());
                break;
            case R.id.Money:
                setFragment(new FourFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, fragment, null).commit();
    }


}