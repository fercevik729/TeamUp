package com.fercevik.programservice.models;

import jakarta.persistence.*;

import java.math.BigInteger;

/**
 * Entity model that represents exercise sets
 * Has a ManyToOne relationship with objects
 * of the Exercise model
 */
@Entity
@Table(name = "sets")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "set_id")
    private BigInteger setId;

    private int setNumber;
    private int reps;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

}
