package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Workout;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WorkoutRepositoryTests {

    @Autowired
    private WorkoutRepository repository;

    @Test
    public void testSaveAndRetrieve() {
        Workout workout = Workout.builder().description("Leg day! :)").name("Monday").date(new Date()).build();
        repository.save(workout);

        var saved = repository.findById(workout.getWorkoutId()).orElse(null);
        assertNotNull(saved);

        List<Exercise> exercises = repository.findExercisesForWorkout(saved.getWorkoutId());
        assertEquals(workout.getWorkoutId(), saved.getWorkoutId());
        assertEquals(workout.getExercises().stream().toList(), exercises);
        assertEquals(workout.getName(), saved.getName());
        assertEquals(workout.getDescription(), saved.getDescription());

        var saved2 = repository.findWorkoutByName("Monday").orElse(null);
        assertNotNull(saved2);

        List<Exercise> exercises2 = repository.findExercisesForWorkout(saved2.getWorkoutId());
        assertEquals(workout.getWorkoutId(), saved2.getWorkoutId());
        assertEquals(workout.getExercises().stream().toList(), exercises2);
        assertEquals(workout.getName(), saved2.getName());
        assertEquals(workout.getDescription(), saved2.getDescription());

        var saved3 = repository.findWorkoutsByDescription("Leg day! :)");
        assert !saved3.isEmpty();

        List<Exercise> exercises3 = repository.findExercisesForWorkout(saved3.get(0).getWorkoutId());
        assertEquals(workout.getWorkoutId(), saved3.get(0).getWorkoutId());
        assertEquals(workout.getExercises().stream().toList(), exercises3);
        assertEquals(workout.getName(), saved3.get(0).getName());
        assertEquals(workout.getDescription(), saved3.get(0).getDescription());
    }

}
