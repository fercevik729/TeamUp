package com.fercevik.programservice.repositories;

import com.fercevik.programservice.models.Exercise;
import com.fercevik.programservice.models.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {
    List<Set> findSetsByExercise(Exercise exercise);

    List<>
}
