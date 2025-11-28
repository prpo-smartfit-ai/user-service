package com.smartfitai.models.dto;

public class ProfileUpdateRequest {
    private Integer age;
    private Double height;
    private Double currentWeight;
    private String gender;
    private String fitnessLevel;
    private String primaryGoal;
    private Integer preferredDaysPerWeek;
    private Integer preferredSessionDuration;

    // default constructor
    public ProfileUpdateRequest() {}

    public ProfileUpdateRequest(Integer age, Double height, Double currentWeight, String gender,
                               String fitnessLevel, String primaryGoal, Integer preferredDaysPerWeek,
                               Integer preferredSessionDuration) {
        this.age = age;
        this.height = height;
        this.currentWeight = currentWeight;
        this.gender = gender;
        this.fitnessLevel = fitnessLevel;
        this.primaryGoal = primaryGoal;
        this.preferredDaysPerWeek = preferredDaysPerWeek;
        this.preferredSessionDuration = preferredSessionDuration;
    }

    // getters and setters
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(Double currentWeight) { this.currentWeight = currentWeight; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFitnessLevel() { return fitnessLevel; }
    public void setFitnessLevel(String fitnessLevel) { this.fitnessLevel = fitnessLevel; }

    public String getPrimaryGoal() { return primaryGoal; }
    public void setPrimaryGoal(String primaryGoal) { this.primaryGoal = primaryGoal; }

    public Integer getPreferredDaysPerWeek() { return preferredDaysPerWeek; }
    public void setPreferredDaysPerWeek(Integer preferredDaysPerWeek) { this.preferredDaysPerWeek = preferredDaysPerWeek; }

    public Integer getPreferredSessionDuration() { return preferredSessionDuration; }
    public void setPreferredSessionDuration(Integer preferredSessionDuration) { this.preferredSessionDuration = preferredSessionDuration; }
}