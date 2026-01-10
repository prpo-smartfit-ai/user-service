package com.smartfitai.models;

import javax.persistence.*;
import javax.json.bind.annotation.JsonbTransient;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Authentication
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    @JsonbTransient
    private String passwordHash;
    
    // Basic Info
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    // Physical Data
    private Double currentWeight; // kg
    private Double height; // cm
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private Integer age;
    
    // Fitness Data
    @Enumerated(EnumType.STRING)
    private FitnessLevel fitnessLevel;
    
    @Enumerated(EnumType.STRING)
    private FitnessGoal primaryGoal;
    
    // Preferences
    private Integer preferredDaysPerWeek;
    private Integer preferredSessionDuration; // minutes
    
    @Column(columnDefinition = "TEXT")
    private String availableEquipment;
    
    // Statistics
    private Integer totalWorkouts = 0;
    private Double totalHours = 0.0;
    
    // Metadata
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum FitnessLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
    
    public enum FitnessGoal {
        WEIGHT_LOSS, MUSCLE_GAIN, ENDURANCE, MAINTENANCE
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public FitnessLevel getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(FitnessLevel fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public FitnessGoal getPrimaryGoal() {
        return primaryGoal;
    }

    public void setPrimaryGoal(FitnessGoal primaryGoal) {
        this.primaryGoal = primaryGoal;
    }

    public Integer getPreferredDaysPerWeek() {
        return preferredDaysPerWeek;
    }

    public void setPreferredDaysPerWeek(Integer preferredDaysPerWeek) {
        this.preferredDaysPerWeek = preferredDaysPerWeek;
    }

    public Integer getPreferredSessionDuration() {
        return preferredSessionDuration;
    }

    public void setPreferredSessionDuration(Integer preferredSessionDuration) {
        this.preferredSessionDuration = preferredSessionDuration;
    }

    public String getAvailableEquipment() {
        return availableEquipment;
    }

    public void setAvailableEquipment(String availableEquipment) {
        this.availableEquipment = availableEquipment;
    }

    public Integer getTotalWorkouts() {
        return totalWorkouts;
    }

    public void setTotalWorkouts(Integer totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
