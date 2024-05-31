package com.example.soft;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProbability extends AppCompatActivity {

    private TextView textViewProbability;
    private String name;
    private int age, gender, hypertension, heartDisease, smoking;
    private float height, weight, bmi;

    private static final String TAG = "MyProbability";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_probability);

        // 사용자가 입력한 정보 가져오기
        name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);
        height = getIntent().getFloatExtra("height", 0);
        weight = getIntent().getFloatExtra("weight", 0);
        gender = getIntent().getIntExtra("gender", 0);
        hypertension = getIntent().getIntExtra("hypertension", 0);
        heartDisease = getIntent().getIntExtra("heartDisease", 0);
        smoking = getIntent().getIntExtra("smoking", 0);
        bmi = weight / ((height / 100) * (height / 100)); // BMI 계산

        // TextView 찾기
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewHeight = findViewById(R.id.textViewHeight);
        TextView textViewWeight = findViewById(R.id.textViewWeight);
        TextView textViewGender = findViewById(R.id.textViewGender);
        TextView textViewHypertension = findViewById(R.id.textViewHypertension);
        TextView textViewHeartDisease = findViewById(R.id.textViewHeartDisease);
        TextView textViewSmoking = findViewById(R.id.textViewSmoking);

        // TextView에 데이터 설정
        textViewName.setText("이름: " + name);
        textViewAge.setText("나이: " + age);
        textViewHeight.setText("키: " + height);
        textViewWeight.setText("몸무게: " + weight);
        textViewGender.setText("성별: " + (gender == 1 ? "남성" : "여성"));
        textViewHypertension.setText("고혈압: " + (hypertension == 1 ? "있음" : "없음"));
        textViewHeartDisease.setText("심장병: " + (heartDisease == 1 ? "있음" : "없음"));
        textViewSmoking.setText("흡연 여부: " + (smoking == 1 ? "흡연" : "비흡연"));

        textViewProbability = findViewById(R.id.textViewProbability);

        // Firebase에서 데이터 가져오기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("data");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sameAgeGenderCount = 0;
                int sameAgeGenderSmokingCount = 0;
                int strokeCount = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // 데이터 가져오기
                    int userAge = ((Long) snapshot.child("age").getValue(Long.class)).intValue();
                    String userGenderString = snapshot.child("gender").getValue(String.class);
                    int userHypertension = ((Long) snapshot.child("hypertension").getValue(Long.class)).intValue();
                    int userHeartDisease = ((Long) snapshot.child("heart_disease").getValue(Long.class)).intValue();
                    String userSmokingStatus = snapshot.child("smoking_status").getValue(String.class);
                    int userStroke = ((Long) snapshot.child("stroke").getValue(Long.class)).intValue();

                    // 성별 변환
                    int userGender = "Male".equals(userGenderString) ? 1 : 0;

                    // 사용자 정보와 비교하여 조건에 맞는지 확인
                    if (userAge / 10 == age / 10 && userGender == gender &&
                            userHypertension == hypertension && userHeartDisease == heartDisease) {
                        sameAgeGenderCount++;

                        // 흡연 여부 확인
                        if ((!userSmokingStatus.equals("never smoked") && smoking == 1) ||
                                (userSmokingStatus.equals("never smoked") && smoking == 0)) {
                            sameAgeGenderSmokingCount++;

                            // 뇌졸중 여부 확인
                            if (userStroke == 1) {
                                strokeCount++;
                            }
                        }
                    }
                }

                Log.d(TAG, "sameAgeGenderCount: " + sameAgeGenderCount);
                Log.d(TAG, "sameAgeGenderSmokingCount: " + sameAgeGenderSmokingCount);
                Log.d(TAG, "strokeCount: " + strokeCount);

                // 뇌졸중 확률 계산
                double probability = 0.0;
                if (sameAgeGenderSmokingCount > 0) {
                    probability = (double) strokeCount / sameAgeGenderSmokingCount;
                }

                // 결과 출력
                textViewProbability.setText("뇌졸중 확률: " + (probability * 100) + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                textViewProbability.setText("데이터 가져오기 실패");
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}
