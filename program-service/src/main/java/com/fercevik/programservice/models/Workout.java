package com.fercevik.programservice.models;

import jakarta.persistence.*;

import java.math.BigInteger;
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
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workout_id")
    private BigInteger workoutId;

    private String name;
    private String description;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
}
