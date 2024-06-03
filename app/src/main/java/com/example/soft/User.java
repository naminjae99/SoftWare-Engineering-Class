package com.example.soft;

public class User {
    private int id;
    private String gender;
    private int age;
    private int hypertension;
    private int heartDisease;
    private float avgGlucoseLevel;
    private float bmi;
    private String smokingStatus;
    private String everMarried;
    private String workType;
    private String residenceType;
    private int stroke;
    private int num;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(int num,int id, String gender, int age, int hypertension, int heartDisease, float avgGlucoseLevel, float bmi, String smokingStatus, String everMarried, String workType, String residenceType, int stroke) {
        this.num=num;
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.hypertension = hypertension;
        this.heartDisease = heartDisease;
        this.avgGlucoseLevel = avgGlucoseLevel;
        this.bmi = bmi;
        this.smokingStatus = smokingStatus;
        this.everMarried = everMarried;
        this.workType = workType;
        this.residenceType = residenceType;
        this.stroke = stroke;
    }

    // Getters and setters
    public int getnum() {
        return num;
    }
    public int getId() {
        return id;
    }


    public String getGender() {
        return gender;
    }



    public int getAge() {
        return age;
    }



    public int getHypertension() {
        return hypertension;
    }



    public int getHeartDisease() {
        return heartDisease;
    }



    public float getAvgGlucoseLevel() {
        return avgGlucoseLevel;
    }



    public float getBmi() {
        return bmi;
    }



    public String getSmokingStatus() {
        return smokingStatus;
    }



    public String getEverMarried() {
        return everMarried;
    }



    public String getWorkType() {
        return workType;
    }



    public String getResidenceType() {
        return residenceType;
    }


    public int getStroke() {
        return stroke;
    }


}
