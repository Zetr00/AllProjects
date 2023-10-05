package com.example.roomdbproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.Random;

public class GlobalActivity extends AppCompatActivity {
    private int idUser = 0;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        db = AppDatabase.getDbInstance(getApplicationContext());

        SharedPreferences settings = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        EditText edL = findViewById(R.id.loginEdit);
        EditText edP = findViewById(R.id.passwordEdit);

        Intent intent = getIntent();
        User user = (User)intent.getSerializableExtra("data");
        edL.setText(user.getLogin());
        edP.setText(user.getPassword());
        idUser = user.getId();

        LoadData();
        updateListsCategory();

        Button goToAllAddMoney = findViewById(R.id.btAllAdd);
        goToAllAddMoney.setOnClickListener(view ->{
            Intent intentAllAddMoney = new Intent(this, allAddMoney.class);
            intentAllAddMoney.putExtra("idUser", idUser);
            startActivity(intentAllAddMoney);
        });

        Button goToAllUpdateMoney = findViewById(R.id.btAllUpdate);
        goToAllUpdateMoney.setOnClickListener(view ->{
            Intent intentAllAddMoney = new Intent(this, allUpdateMoney.class);
            intentAllAddMoney.putExtra("idUser", idUser);
            startActivity(intentAllAddMoney);
        });

        Button button = findViewById(R.id.saveChanges);
        button.setOnClickListener(view ->{
            User userP = new User(
                    edL.getText().toString(),
                    edP.getText().toString()
            );
            AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
            userP.setId(idUser);
            db.userDAO().updateUser(userP);

            editor.putInt("idUser", userP.getId());
            editor.putString("login", userP.getLogin());
            editor.putString("password", userP.getPassword());
            editor.apply();

            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
        });

        Button unlog = findViewById(R.id.unLog);
        unlog.setOnClickListener(view ->{
            editor.putInt("idUser", -1);
            editor.putString("login", "notFound");
            editor.putString("password", "notFound");
            editor.apply();

            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            finish();
        });

        Button addMoney = findViewById(R.id.btAddMoney);
        addMoney.setOnClickListener(view ->{
            try {
                EditText edSum = findViewById(R.id.edAddSum);
                int numberPlus = Integer.parseInt(edSum.getText().toString()); //число прибавления

                TextView tvBalance = findViewById(R.id.tvBalance);
                int balance = Integer.parseInt(tvBalance.getText().toString()); //изначальный баланс

                if((balance+numberPlus) >= 0){
                    balance += numberPlus;
                    tvBalance.setText(String.valueOf(balance));
                    User userTemp = db.userDAO().getUser(idUser);
                    userTemp.setBalance(balance);
                    db.userDAO().updateUser(userTemp);
                    edSum.setText("");

                    AddNewInHistory(numberPlus, "Add");
                    updateListsCategory();
                } else{
                    String temp = "f";
                    Integer.parseInt(temp);
                }
            }
            catch(Exception exept){
                Toast.makeText(this, "Неверное число", Toast.LENGTH_SHORT).show();
            }
        });

        Button editMoney = findViewById(R.id.btEditMoney);
        editMoney.setOnClickListener(view ->{
            try {
                EditText edMoney = findViewById(R.id.edUpdateSum);
                int numberPlus = Integer.parseInt(edMoney.getText().toString()); //число списания

                TextView tvBalance = findViewById(R.id.tvBalance);
                int balance = Integer.parseInt(tvBalance.getText().toString()); //изначальный баланс

                if((balance-numberPlus) >= 0){
                    balance -= numberPlus;
                    tvBalance.setText(String.valueOf(balance));
                    User userTemp = db.userDAO().getUser(idUser);
                    userTemp.setBalance(balance);
                    db.userDAO().updateUser(userTemp);
                    edMoney.setText("");

                    AddNewInHistory(numberPlus, "Update");
                    updateListsCategory();
                } else{
                    String temp = "f";
                    Integer.parseInt(temp);
                }
            }
            catch(Exception exept){
                Toast.makeText(this, "Ошибка списания", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListsCategory() {
        TextView tvCategoriesAdd = findViewById(R.id.tvCategoriesAdd);
        TextView tvCategoriesUpdate = findViewById(R.id.tvCategoriesUpdate);

        tvCategoriesAdd.setText("");
        tvCategoriesUpdate.setText("");
        List<History> historiesCat = db.historyDAO().getHistoryAtUser(idUser);
        for (int b = 0; b<historiesCat.size(); b++){
            if(historiesCat.get(b).getOperation().equals("Add"))
                tvCategoriesAdd.append("\t" + historiesCat.get(b).getCategory() + "\n");
            else
                tvCategoriesUpdate.append("\t" + historiesCat.get(b).getCategory() + "\n");
        }
    }

    private void AddNewInHistory(int numberPlus, String operation) {
        EditText edCat = findViewById(R.id.etCategory);
        int red = 0;
        int green = 0;
        int blue = 0;
        boolean find = false;

        List<History> histories = db.historyDAO().getHistoryAtUser(idUser);
        for (int g = 0; g < histories.size(); g++){
            if(edCat.getText().toString().equals(histories.get(g).getCategory())){
                History history = histories.get(g);
                history.setSum(history.getSum() + numberPlus);
                db.historyDAO().updateHistory(history);
                find = true;
            }
        }
        if(!find){
            addNewHistory(red, green, blue, numberPlus, edCat, operation);
        }
        edCat.setText("");
    }

    private void addNewHistory(int red, int green, int blue, int numberPlus, EditText edCat, String operation){
        Random rand = new Random();
        for (int i = 0; i < 3; i++){
            red = rand.nextInt();
            green = rand.nextInt();
            blue = rand.nextInt();
            while(red < 0) red+=255;
            while(green < 0) green+=255;
            while(blue < 0) blue+=255;
        }
        History history = new History(numberPlus, edCat.getText().toString(), operation, idUser, red,green,blue);
        db.historyDAO().insertHistory(history);
    }

    private void LoadData(){
        User user = db.userDAO().getUser(idUser);

        TextView tvBalance = findViewById(R.id.tvBalance);
        tvBalance.setText(String.valueOf(user.getBalance()));
    }
}