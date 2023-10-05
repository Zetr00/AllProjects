package com.example.roomdbproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class allUpdateMoney extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_update_money);

        setChart();
    }

    public void setChart(){
        PieChartView chart = findViewById(R.id.chart);
        List<SliceValue> values = new ArrayList<SliceValue>();

        Intent intent = getIntent();
        int idUser = (Integer) intent.getSerializableExtra("idUser");

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<History> history = db.historyDAO().getHistoryAtUser(idUser);

        TextView list = findViewById(R.id.listOperations);

        for (int i = 0; i < history.size(); ++i) {
            if(history.get(i).getOperation().equals("Update")){
                Random random = new Random();
                SliceValue sliceValue = new SliceValue();
                sliceValue.setValue(history.get(i).getSum());
                sliceValue.setColor(Color.rgb(history.get(i).getRed(), history.get(i).getGreen(), history.get(i).getBlue()));
                values.add(sliceValue);

                list.append("Id: " + history.get(i).getId() + "; Сумма: " + history.get(i).getSum() + "; Категория: "
                        + history.get(i).getCategory() + "\n");

                TextView textViewColor = new TextView(this);
                textViewColor.setWidth(20);
                textViewColor.setHeight(50);
                textViewColor.append("▣");
                textViewColor.setTextColor(Color.rgb(history.get(i).getRed(), history.get(i).getGreen(), history.get(i).getBlue()));

                LinearLayout colorLayout1 = findViewById(R.id.colorLayout1);
                colorLayout1.addView(textViewColor);
            }
        }

        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        data.setValueLabelTextSize(16);
        data.setHasCenterCircle(true);
        data.setCenterText1("Расходы");
        data.setCenterText1Color(Color.rgb(255,255,255));
        data.setCenterText1FontSize(24);

        chart.setInteractive(false);
        chart.setPieChartData(data);
    }
}