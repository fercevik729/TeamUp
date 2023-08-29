package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT e FROM exercises e WHERE e.description LIKE :desc")
    List<Exercise> findExercisesByDescription(String desc);

    @Query("SELECT e FROM exercises e WHERE e.target LIKE :target")
    List<Exercise> findExercisesByTarget(String target);
}
