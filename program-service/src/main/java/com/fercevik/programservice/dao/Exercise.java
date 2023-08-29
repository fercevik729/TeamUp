package com.fercevik.programservice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long exerciseId;

    private String name;
    private String description;
    private String target;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Set> sets = new ArrayList<>();
}
