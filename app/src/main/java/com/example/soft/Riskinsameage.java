package com.example.soft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
public class Riskinsameage extends AppCompatActivity {

    private PieChart pieChartRisk;
    private TextView textViewRiskDistribution;
    private TextView textViewDescription;
    private String name;
    private int age;
    private float height, weight, bmi;
    private DatabaseReference databaseReference;
    private float fage;
    private static final String TAG = "Riskinsameage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riskinsameage);

        // 파이 차트 초기화
        pieChartRisk = findViewById(R.id.pieChartRisk);

        // 텍스트뷰 초기화
        textViewRiskDistribution = findViewById(R.id.textViewRiskDistribution);


        // 사용자가 입력한 정보 가져오기
        name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);
        height = getIntent().getFloatExtra("height", 0);
        weight = getIntent().getFloatExtra("weight", 0);
        bmi = weight / ((height / 100) * (height / 100)); // BMI 계산

        fage = age;

        // Firebase에서 데이터 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "Initialized Firebase Database reference");
        databaseReference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int normalCount = 0;
                int riskCount = 0;
                int diabetesCount = 0;
                int sameAgeCount = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // 데이터 가져오기
                    Float userAge = snapshot.child("age").getValue(Float.class);
                    Float userBloodSugar = snapshot.child("avg_glucose_level").getValue(Float.class); // 당뇨 수치

                    if ((Math.abs(fage - userAge) <= 10)) {
                        sameAgeCount++;
                        if (userBloodSugar != null) {
                            if (userBloodSugar <= 100) {
                                normalCount++;
                            } else if (userBloodSugar <= 126) {
                                riskCount++;
                            } else {
                                diabetesCount++;
                            }
                        }
                    }
                }

                Log.d(TAG, "normalCount: " + normalCount);
                Log.d(TAG, "riskCount: " + riskCount);
                Log.d(TAG, "diabetesCount: " + diabetesCount);

                // 파이 차트에 데이터 설정
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry((float) normalCount / sameAgeCount * 100, "정상"));
                entries.add(new PieEntry((float) riskCount / sameAgeCount * 100, "위험"));
                entries.add(new PieEntry((float) diabetesCount / sameAgeCount * 100, "당뇨"));

                PieDataSet dataSet = new PieDataSet(entries, "");

                // 색상 설정
                ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(
                        ContextCompat.getColor(Riskinsameage.this, R.color.normal), // 연두색: 정상
                        ContextCompat.getColor(Riskinsameage.this, R.color.risk), // 노란색: 위험
                        ContextCompat.getColor(Riskinsameage.this, R.color.diabetes) // 빨간색: 당뇨
                ));

                dataSet.setColors(colors);

                PieData pieData = new PieData(dataSet);
                pieChartRisk.setData(pieData);
                pieChartRisk.invalidate(); // 차트 다시 그리기

                // 사용자의 동연령대 당뇨병 위험도를 표시

                String riskDistributionText = name + "님의 동연령대 당뇨병 위험도";
                textViewRiskDistribution.setText(riskDistributionText);

                // 다음 페이지로 이동하는 버튼 설정
                Button buttonNext = findViewById(R.id.buttonNext);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(Riskinsameage.this, Recommend.class);
                        intent.putExtra("name", name);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("bmi", bmi);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                textViewRiskDistribution.setText("데이터 가져오기 실패");
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}
