package com.smartfitai.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Progress> progressRecords = new ArrayList<>();
    
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
}
