package com.hdychi.piechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hdychi.piechart.chart.PieChart;
import com.hdychi.piechart.chart.PieChartItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PieChart pieChart = findViewById(R.id.pie_chart);
        for (int i = 0;i < 3;i++){
            pieChart.addItem(new PieChartItem("第" + (i +1)+ "个",i+1));
        }
    }
}
