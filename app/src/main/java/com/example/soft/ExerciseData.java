package com.example.soft;
public class ExerciseData {
    private String name;
    private String steps;
    private String target;

    public ExerciseData() {
        // 기본 생성자 (Firebase에서 필요)
    }

    public ExerciseData(String name, String steps, String target) {
        this.name = name;
        this.steps = steps;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
