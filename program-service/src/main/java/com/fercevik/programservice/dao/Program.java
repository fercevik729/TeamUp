package com.fercevik.programservice.dao;

import com.fercevik.programservice.shared.WeightUnits;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Entity model that represents programs.
 * Has a OneToMany relationship with objects
 * of the Workout model.
 */
@Entity(name = "programs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "program_id")
    private Long programId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    private String name;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = false;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private LocalDate createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private WeightUnits units = WeightUnits.POUNDS;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "program_tags", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "tags")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Workout> workouts = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
