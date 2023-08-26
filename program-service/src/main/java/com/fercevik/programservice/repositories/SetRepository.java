package com.fercevik.programservice.repositories;

import com.fercevik.programservice.models.Exercise;
import com.fercevik.programservice.models.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {
    List<Set> findSetsByExercise(Exercise exercise);

}
