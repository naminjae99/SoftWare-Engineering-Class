package com.example.soft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.widget.MediaController;

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

public class ExerciseActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ExercisePagerAdapter adapter;
    private List<ExerciseData> exerciseList;
    private TextView textname;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_exercise);

        String name = getIntent().getStringExtra("name");
        textname = findViewById(R.id.textname);
        Button buttonNext = findViewById(R.id.nextButton);
        videoView = findViewById(R.id.videoView);

        viewPager = findViewById(R.id.viewPager);
        exerciseList = new ArrayList<>();
        adapter = new ExercisePagerAdapter(exerciseList);
        viewPager.setAdapter(adapter);

        textname.setText(name + " 님께 추천하는 운동");

        // 비디오 뷰 설정
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.exercise_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseActivity.this, GoodFoodActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("exercises");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name1 = snapshot.child("name").getValue(String.class);
                    String steps = snapshot.child("steps").getValue(String.class);
                    String target = snapshot.child("target").getValue(String.class);
                    // "*"을 기준으로 문자열을 분할하고 각 부분을 새 줄로 표시하여 텍스트뷰에 설정
                    String[] stepsArray = steps.split("\\*");
                    StringBuilder formattedSteps = new StringBuilder();
                    for (String step : stepsArray) {
                        formattedSteps.append(" ").append(step.trim()).append("\n\n");
                    }

                    exerciseList.add(new ExerciseData(name1, formattedSteps.toString(), target));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 오류 처리
            }
        });
    }
}
