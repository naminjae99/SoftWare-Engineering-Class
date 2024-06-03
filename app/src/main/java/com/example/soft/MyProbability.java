package com.example.soft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MyProbability extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewProbability;
    private String name;
    private int age, hypertension, heartDisease, smoking;
    private float height, weight, bmi;
    private int gender; // 성별을 문자열로 변경
    private DatabaseReference databaseReference;
    private float fage;
    private static final String TAG = "MyProbability";

    // PieChart 선언
    private PieChart pieChart;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_probability);

        // PieChart 초기화
        pieChart = findViewById(R.id.pieChart);

        // Next 버튼 초기화
        buttonNext = findViewById(R.id.buttonNext);

        // 사용자가 입력한 정보 가져오기
        name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);
        height = getIntent().getFloatExtra("height", 0);
        weight = getIntent().getFloatExtra("weight", 0);
        smoking = getIntent().getIntExtra("smoking", 0);
        bmi = weight / ((height / 100) * (height / 100)); // BMI 계산

        fage = age;

        // TextView 찾기
        textViewName = findViewById(R.id.textViewName);
        // TextView에 데이터 설정
        textViewName.setText(name + "님의 당뇨병 발병 확률");

        textViewProbability = findViewById(R.id.textViewProbability);

        // Firebase에서 데이터 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "Initialized Firebase Database reference");
        databaseReference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sameAgeGenderSmokingCount = 0;
                int strokeCount = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // 데이터 가져오기
                    Float userAge = snapshot.child("age").getValue(Float.class);
                    String userSmokingStatus = snapshot.child("smoking_status").getValue(String.class);
                    int userStroke = snapshot.child("stroke").getValue(Integer.class);
                    int usernum = snapshot.child("num").getValue(Integer.class);
                    Float userbmi = snapshot.child("bmi").getValue(Float.class);
                    Log.d(TAG, "data smoke: " + userSmokingStatus + "data num: " + usernum);

                    int usersmoke = 0;

                    if (userSmokingStatus.equals("never smoked") || userSmokingStatus.equals("Unknown"))
                        usersmoke = 0;
                    else
                        usersmoke = 1;

                    // 흡연 여부 확인
                    if ((Math.abs(fage - userAge) <= 10) && (smoking == usersmoke) && (Math.abs(bmi - userbmi) <= 10)) {
                        sameAgeGenderSmokingCount++;
                        // 뇌졸중 여부 확인
                        if (userStroke == 1) {
                            strokeCount++;
                        }
                    }
                }

                Log.d(TAG, "sameAgeGenderSmokingCount: " + sameAgeGenderSmokingCount);
                Log.d(TAG, "strokeCount: " + strokeCount);
                // 뇌졸중 확률 계산
                double probability = 0;
                if (sameAgeGenderSmokingCount > 0) {
                    probability = (double) strokeCount / sameAgeGenderSmokingCount * 100;
                }
                Log.d(TAG, "확률: " + probability);

                // 소수점 첫째 자리까지 표시
                textViewProbability.setText(String.format("%.1f%%", probability));

                // Pie Chart에 데이터 설정
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry((float) probability, "뇌졸중 발병 확률"));
                entries.add(new PieEntry((float) (100 - probability), "기타"));

                PieDataSet dataSet = new PieDataSet(entries, "");

                // 색상 설정
                ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(
                        getResources().getColor(R.color.stroke_probability), // 예: 뇌졸중 발병 확률 색상
                        getResources().getColor(R.color.others) // 예: 기타 항목 색상
                ));
                dataSet.setColors(colors);

                PieData pieData = new PieData(dataSet);
                pieChart.setData(pieData);
                pieChart.invalidate(); // 차트 다시 그리기
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                textViewProbability.setText("데이터 가져오기 실패");
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });

        // Next 버튼 클릭 시 실행될 리스너 설정
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
                Intent intent = new Intent(MyProbability.this, Riskinsameage.class);
                startActivity(intent);
            }
        });
    }
}
