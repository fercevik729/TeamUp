package com.fercevik.programservice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Objects;

/**
 * Entity model that represents exercise sets
 * Has a ManyToOne relationship with objects
 * of the Exercise model
 */
@Entity(name = "sets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "set_id")
    private long setId;

    private int reps;
    private double weight;
    private Duration duration;
}
