package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.dao.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @Query("SELECT w.exercises FROM workouts w WHERE w.workoutId = :workoutId")
    List<Set> findExercisesForWorkout(Long workoutId);

}
