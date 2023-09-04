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
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @Query("SELECT w.exercises FROM workouts w WHERE w.workoutId = :workoutId")
    List<Exercise> findExercisesForWorkout(Long workoutId);

    @Query("SELECT w FROM workouts w WHERE w.description LIKE :desc")
    List<Workout> findWorkoutsByDescription(String desc);

    @Query("SELECT w FROM workouts w WHERE w.name LIKE :name")
    Optional<Workout> findWorkoutByName(String name);
}
