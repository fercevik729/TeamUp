package com.fercevik.programservice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workout_id")
    private Long workoutId;

    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        date = new Date();
    }
}
