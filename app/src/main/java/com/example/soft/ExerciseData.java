package com.example.soft;
import java.util.*;

public class ExerciseData {
    private List<Exercise> exercises;

    public ExerciseData() {
    }

    public ExerciseData(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public static class Exercise {
        private String name;
        private String target;
        private String steps;

        public Exercise() {
        }

        public Exercise(String name, String target, String steps) {
            this.name = name;
            this.target = target;
            this.steps = steps;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }
    }
}
