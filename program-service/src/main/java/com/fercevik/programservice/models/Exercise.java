package com.fercevik.programservice.models;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity model that represents exercises.
 * Has a ManyToOne relationship with objects
 * of the Workout model.
 * Has a OneToMany relationship with objects
 * of the Set model.
 */
@Entity(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long exerciseId;

    private String name;

    private String description;

    private String target;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<Set> sets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;
}
