package com.example.soft;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<ExerciseData.Exercise> exerciseList;

    public ExerciseAdapter(List<ExerciseData.Exercise> exerciseList) {
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
        ExerciseData.Exercise exercises = exerciseList.get(position);
        holder.exerciseName.setText(exercises.getName());
        holder.exerciseTarget.setText(exercises.getTarget());
        holder.exerciseSteps.setText(exercises.getSteps());

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView exerciseTarget;
        TextView exerciseSteps;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            exerciseTarget = itemView.findViewById(R.id.exercise_target);
            exerciseSteps = itemView.findViewById(R.id.exercise_steps);
        }
    }
}
