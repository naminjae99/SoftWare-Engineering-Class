// BadFoodActivity.java
package com.example.soft;
import android.content.Intent;
import android.os.Bundle;
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

public class BadFoodActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView textView;
    private Button retryButton; // 리트라이 버튼 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_food);

        // Firebase 데이터베이스의 레퍼런스 설정
        databaseReference = FirebaseDatabase.getInstance().getReference().child("foods");

        // 사용자 이름 가져오기
        Intent intent = getIntent();
        String username = intent.getStringExtra("name");

        // 사용자 이름과 함께 텍스트뷰에 설정
        textView = findViewById(R.id.textView);
        textView.setText(username + " 님이 주의해야 할 음식");

        // 나쁜 음식 데이터 로드 및 표시
        loadAndDisplayFoods("bad_foods");

        // 리트라이 버튼 클릭 시 스플래시 액티비티로 이동
        retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BadFoodActivity.this, SplashActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });
    }

    // Firebase에서 데이터를 로드하고 해당 데이터를 표시하는 메서드
    private void loadAndDisplayFoods(String path) {
        // Firebase 데이터베이스에서 데이터 로드
        databaseReference.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FoodData> foodsList = new ArrayList<>();
                // 데이터 스냅샷을 순회하면서 음식 데이터를 가져와 리스트에 추가
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String description = snapshot.child("description").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String url = snapshot.child("url").getValue(String.class);
                    String imageResId = snapshot.child("imageResId").getValue(String.class);
                    foodsList.add(new FoodData(description, name, url, imageResId));
                }
                // ViewPager2로 음식 데이터를 보여주는 어댑터 설정
                ViewPager2 viewPager2 = findViewById(R.id.viewPager);
                FoodAdapter adapter = new FoodAdapter(foodsList);
                viewPager2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }
}
