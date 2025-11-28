package com.smartfitai.models.dto;

import com.smartfitai.models.User;

public class UserProfileResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Double currentWeight;
    private Double height;
    private String gender;
    private Integer age;
    private String fitnessLevel;
    private String primaryGoal;
    private Integer preferredDaysPerWeek;
    private Integer preferredSessionDuration;
    private String availableEquipment;
    private Integer totalWorkouts;
    private Double totalHours;
    private String createdAt;
    private String updatedAt;

    public UserProfileResponse() {}

    public UserProfileResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.currentWeight = user.getCurrentWeight();
        this.height = user.getHeight();
        this.gender = user.getGender() != null ? user.getGender().toString() : null;
        this.age = user.getAge();
        this.fitnessLevel = user.getFitnessLevel() != null ? user.getFitnessLevel().toString() : null;
        this.primaryGoal = user.getPrimaryGoal() != null ? user.getPrimaryGoal().toString() : null;
        this.preferredDaysPerWeek = user.getPreferredDaysPerWeek();
        this.preferredSessionDuration = user.getPreferredSessionDuration();
        this.availableEquipment = user.getAvailableEquipment();
        this.totalWorkouts = user.getTotalWorkouts();
        this.totalHours = user.getTotalHours();
        this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
        this.updatedAt = user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null;
    }

    // just getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public Double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(Double currentWeight) { this.currentWeight = currentWeight; }
    
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getFitnessLevel() { return fitnessLevel; }
    public void setFitnessLevel(String fitnessLevel) { this.fitnessLevel = fitnessLevel; }
    
    public String getPrimaryGoal() { return primaryGoal; }
    public void setPrimaryGoal(String primaryGoal) { this.primaryGoal = primaryGoal; }
    
    public Integer getPreferredDaysPerWeek() { return preferredDaysPerWeek; }
    public void setPreferredDaysPerWeek(Integer preferredDaysPerWeek) { this.preferredDaysPerWeek = preferredDaysPerWeek; }
    
    public Integer getPreferredSessionDuration() { return preferredSessionDuration; }
    public void setPreferredSessionDuration(Integer preferredSessionDuration) { this.preferredSessionDuration = preferredSessionDuration; }
    
    public String getAvailableEquipment() { return availableEquipment; }
    public void setAvailableEquipment(String availableEquipment) { this.availableEquipment = availableEquipment; }
    
    public Integer getTotalWorkouts() { return totalWorkouts; }
    public void setTotalWorkouts(Integer totalWorkouts) { this.totalWorkouts = totalWorkouts; }
    
    public Double getTotalHours() { return totalHours; }
    public void setTotalHours(Double totalHours) { this.totalHours = totalHours; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
