package com.example.soft;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailActivity extends AppCompatActivity {
    private TextView textExerciseName;
    private TextView textExerciseDescription;
    private TextView textExerciseTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        textExerciseName = findViewById(R.id.textExerciseName);
        textExerciseDescription = findViewById(R.id.textExerciseDescription);
        textExerciseTarget = findViewById(R.id.textExerciseTarget);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String target = getIntent().getStringExtra("target");

        textExerciseName.setText(name);
        textExerciseDescription.setText(description);
        textExerciseTarget.setText(target);
    }
}
