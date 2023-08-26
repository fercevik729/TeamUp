package com.fercevik.programservice.repositories;

import com.fercevik.programservice.models.Exercise;
import com.fercevik.programservice.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findExercisesByWorkout(Workout workout);

    @Query("SELECT e FROM exercises e WHERE e.description LIKE :desc")
    List<Exercise> findExercisesByDescription(String desc);

    @Query("SELECT e FROM exercises e WHERE e.target LIKE :target")
    List<Exercise> findExercisesByTarget(String target);
}
