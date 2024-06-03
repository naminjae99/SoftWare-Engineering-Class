package com.example.soft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Recommend extends AppCompatActivity {
    private  TextView textViewWeight;
    private BarChart barChart;
    private TextView textViewGraphDescription;
    private TextView textViewWeightChange;
    private String name;
    private float averageBMI;
    private float userBMI;
    private float averageNormalBMI;
    private int height;
    private int weight;
    private TextView textbarchart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        averageBMI = intent.getFloatExtra("averageBMI", 0);
        userBMI = intent.getFloatExtra("bmi", 0);
        averageNormalBMI = intent.getFloatExtra("averageNormalBMI", 0);
        height = intent.getIntExtra("height", 0);
        weight = intent.getIntExtra("weight", 0);

        // 막대 그래프 초기화
        barChart = findViewById(R.id.barChart);

        // 텍스트뷰 초기화
        textbarchart=findViewById(R.id.textbarchart);
        textViewGraphDescription = findViewById(R.id.textViewGraphDescription);
        textViewWeight=findViewById(R.id.textViewWeight);
        textViewWeightChange=findViewById(R.id.textViewWeightChange);
        // 데이터 설정
        setChartData(averageBMI, userBMI, averageNormalBMI);
        displayWeightChange();
    }

    private void setChartData(float averageBMI, float userBMI, float averageNormalBMI) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, averageBMI));
        entries.add(new BarEntry(1, userBMI));
        entries.add(new BarEntry(2, averageNormalBMI));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(new int[]{R.color.haneal, R.color.haneal, R.color.haneal}, this);
        dataSet.setDrawValues(true); // 막대 그래프에 수치 표시

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.7f); // 바 너비 설정

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setEnabled(false);
        barChart.setDrawGridBackground(false);

        // X축 값 설정
        final String[] xAxisLabels = new String[]{""};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        barChart.invalidate(); // 차트 다시 그리기

        // 텍스트뷰에 데이터 설정
        textbarchart.setText(name+" 님의 동연령대 BMI 비교");
        textViewGraphDescription.setText(name + "님의 권장 변화량");
    }

    private void displayWeightChange() {
        if(userBMI <= averageNormalBMI){
            textViewWeightChange.setText(name+"님은 정상입니다");
        }
        else{
            for(int i=0;i<weight;i++){
                float tmp=(weight-i)/(height * height) / 10000;
                if(Math.abs(tmp-averageNormalBMI)<1){
                    textViewWeightChange.setText(name+" 님의 당뇨병 예방을 위해 아래 항목이 권장됩니다.");
                    textViewWeight.setText("1. 몸무게"+tmp+" kg 감량\\n2. 금연");
                    break;
                }
            }
        }
    }
}
