package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.utils.RepoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = "spring.cloud.vault.enabled=false")
public class WorkoutRepositoryTests {

    @Autowired
    private WorkoutRepository repository;

    @Test
    public void testSaveAndFind() {
        var workout = RepoUtils.createWorkout();
        repository.save(workout);

        var saved = repository.findById(workout.getWorkoutId()).orElse(null);
        assertNotNull(saved);

        List<Exercise> exercises = repository.findExercisesForWorkout(saved.getWorkoutId());
        assertEquals(workout.getWorkoutId(), saved.getWorkoutId());
        assertEquals(workout.getExercises().stream().toList(), exercises);
        assertEquals(workout.getName(), saved.getName());
        assertEquals(workout.getDescription(), saved.getDescription());

    }
    @Test
    public void testSaveAndFindByName() {
        var workout = RepoUtils.createWorkout();
        repository.save(workout);

        var saved = repository.findWorkoutByName("Monday").orElse(null);
        assertNotNull(saved);

        List<Exercise> exercises2 = repository.findExercisesForWorkout(saved.getWorkoutId());
        assertEquals(workout.getWorkoutId(), saved.getWorkoutId());
        assertEquals(workout.getExercises().stream().toList(), exercises2);
        assertEquals(workout.getName(), saved.getName());
        assertEquals(workout.getDescription(), saved.getDescription());

    }
    @Test
    public void testSaveAndFindByDescription() {
        var workout = RepoUtils.createWorkout();
        repository.save(workout);

        var saved = repository.findWorkoutsByDescription("Leg day! :)");
        assert !saved.isEmpty();

        List<Exercise> exercises3 = repository.findExercisesForWorkout(saved.get(0).getWorkoutId());
        assertEquals(workout.getWorkoutId(), saved.get(0).getWorkoutId());
        assertEquals(workout.getExercises().stream().toList(), exercises3);
        assertEquals(workout.getName(), saved.get(0).getName());
        assertEquals(workout.getDescription(), saved.get(0).getDescription());
    }

}
