package com.example.soft;

public class User {
    public int id;
    public int gender;
    public int age;
    public int hypertension;
    public int heart_disease;
    public float avg_glucose_level; // 수정
    public float bmi; // 수정
    public String smoking_status;
    public String ever_married;
    public String work_type;
    public String Residence_type;
    public int stroke;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Problem.class)
    }

    public User(int id, int gender, int age, int hypertension, int heart_disease, String ever_married,
                String work_type, String Residence_type, float avg_glucose_level, float bmi, String smoking_status,
                int stroke) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.hypertension = hypertension;
        this.heart_disease = heart_disease;
        this.ever_married = ever_married;
        this.work_type = work_type;
        this.Residence_type = Residence_type;
        this.avg_glucose_level = avg_glucose_level;
        this.bmi = bmi;
        this.smoking_status = smoking_status;
        this.stroke = stroke;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getHypertension() {
        return hypertension;
    }

    public int getHeartDisease() {
        return heart_disease;
    }

    public float getAvgGlucoseLevel() { // 수정
        return avg_glucose_level;
    }

    public float getBmi() { // 수정
        return bmi;
    }

    public String getSmokingStatus() {
        return smoking_status;
    }

    public String getEverMarried() {
        return ever_married;
    }

    public String getWorkType() {
        return work_type;
    }

    public String getResidenceType() {
        return Residence_type;
    }

    public int getStroke() {
        return stroke;
    }
}
