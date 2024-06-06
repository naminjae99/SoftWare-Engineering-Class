package com.example.soft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GoodExerciseActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;


    private TextView textname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_exercise);

        String name = getIntent().getStringExtra("name");
        textname = findViewById(R.id.textname);
        Button buttonNext = findViewById(R.id.nextButton);

        textname.setText(name + " 님께 추천하는 운동");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("exercises");
        loadAndDisplayExercises();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodExerciseActivity.this,GoodFoodActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });


    }

    private void loadAndDisplayExercises() {
        // Firebase 데이터베이스에서 데이터 로드
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ExerciseData.Exercise> exerciseList = new ArrayList<>();
                // 데이터 스냅샷을 순회하면서 운동 데이터를 가져와 리스트에 추가
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name1 = snapshot.child("name").getValue(String.class);
                    String target = snapshot.child("target").getValue(String.class);
                    String steps = snapshot.child("steps").getValue(String.class);
                    exerciseList.add(new ExerciseData.Exercise(name1, target, steps));
                }

                // ViewPager2로 운동 데이터를 보여주는 어댑터 설정
                ViewPager2 viewPager2 = findViewById(R.id.viewPager);
                ExerciseAdapter adapter = new ExerciseAdapter(exerciseList);
                viewPager2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }
}
