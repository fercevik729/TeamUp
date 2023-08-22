package com.fercevik.programservice.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "program_id")
    private Long programId;

    private UUID ownerId;
    private String name;

    @Column(nullable = false)
    private boolean active = false;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();
}
