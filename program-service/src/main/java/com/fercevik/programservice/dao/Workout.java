package com.fercevik.programservice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity model that represents workouts.
 * Has a ManyToOne relationship with objects
 * of the Program model.
 * Has a OneToMany relationship with objects
 * of the Exercise model.
 */
@Entity(name = "workouts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workout_id")
    private long workoutId;

    private String name;
    private String description;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Exercise> exercises = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }
}
