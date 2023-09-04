package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.dao.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT e FROM exercises e WHERE e.description LIKE :desc")
    List<Exercise> findExercisesByDescription(String desc);

    @Query("SELECT e FROM exercises e WHERE e.name LIKE :name")
    Optional<Exercise> findExerciseByName(String name);

    @Query("SELECT e FROM exercises e WHERE e.target LIKE :target")
    List<Exercise> findExercisesByTarget(String target);

    @Query("SELECT e.sets FROM exercises e WHERE e.exerciseId = :exerciseId")
    List<Set> findSetsByExercise(Long exerciseId);
}
