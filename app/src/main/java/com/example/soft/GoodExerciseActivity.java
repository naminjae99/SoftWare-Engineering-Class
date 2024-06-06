package com.example.soft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
public class GoodExerciseActivity extends AppCompatActivity {
    private String name;
    private DatabaseReference databaseReference;

    private static final String TAG = "GoodExerciseActivity";
    private TextView textname;
    private TextView description;
    private VideoView videoView;
    private ListView exerciseListView;
    private Button buttonNext;

    private ArrayList<String> exerciseNames;
    private ArrayList<String> exerciseDescriptions;
    private ArrayList<String> exerciseTargets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_exercise);

        name = getIntent().getStringExtra("name");
        textname = findViewById(R.id.textname);
        description = findViewById(R.id.description);
        videoView = findViewById(R.id.videoView);
        exerciseListView = findViewById(R.id.exerciseListView);
        buttonNext = findViewById(R.id.buttonNext);

        textname.setText(name + " 님께 추천하는 운동");

        exerciseNames = new ArrayList<>();
        exerciseDescriptions = new ArrayList<>();
        exerciseTargets = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "Initialized Firebase Database reference");

        // Firebase 데이터 가져오기
        databaseReference.child("exercises").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // description
                String data_description = dataSnapshot.child("description").getValue(String.class);
                description.setText(data_description);

                 videoView.findViewById(R.id.videoView);
                // URL for video
                String data_url = dataSnapshot.child("url").getValue(String.class);

                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.exercise_video);
                videoView.setVideoURI(videoUri);
                videoView.start();


                // Exercises List
                for (DataSnapshot snapshot : dataSnapshot.child("lists").getChildren()) {
                    String data_name = snapshot.child("name").getValue(String.class);

                    // steps as individual nodes
                    StringBuilder stepsBuilder = new StringBuilder();
                    for (DataSnapshot stepSnapshot : snapshot.child("steps").getChildren()) {
                        stepsBuilder.append(stepSnapshot.getValue(String.class)).append("\n");
                    }
                    String data_steps = stepsBuilder.toString().trim();

                    String data_target = snapshot.child("target").getValue(String.class);

                    exerciseNames.add(data_name);
                    exerciseDescriptions.add(data_steps);
                    exerciseTargets.add(data_target);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(GoodExerciseActivity.this, android.R.layout.simple_list_item_1, exerciseNames);
                exerciseListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });

        // 리스트뷰 아이템 클릭 리스너 설정
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GoodExerciseActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("name", exerciseNames.get(position));
                intent.putExtra("description", exerciseDescriptions.get(position));
                intent.putExtra("target", exerciseTargets.get(position));
                startActivity(intent);
            }
        });

        // Next 버튼 클릭 시 실행될 리스너 설정
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
                Intent intent = new Intent(GoodExerciseActivity.this, GoodFoodActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}
