package com.example.soft;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class ExercisePagerAdapter extends RecyclerView.Adapter<ExercisePagerAdapter.ExerciseViewHolder> {

    private List<ExerciseData> exerciseList;

    public ExercisePagerAdapter(List<ExerciseData> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseData exercise = exerciseList.get(position);
        holder.nameTextView.setText(exercise.getName());
        holder.stepsTextView.setText(exercise.getSteps());
        holder.targetTextView.setText(exercise.getTarget());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView stepsTextView;
        TextView targetTextView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exercise_name);
            stepsTextView = itemView.findViewById(R.id.exercise_steps);
            targetTextView = itemView.findViewById(R.id.exercise_target);
        }
    }
}
