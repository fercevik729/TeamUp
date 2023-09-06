package com.fercevik.programservice.repositories;

import com.fercevik.programservice.utils.RepoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = "spring.cloud.vault.enabled=false")
public class ExerciseRepositoryTests {

    @Autowired
    private ExerciseRepository repository;

    @Test
    public void testSaveAndFind() {
        var exercise = RepoUtils.createExercise();
        repository.save(exercise);

        var saved = repository.findById(exercise.getExerciseId()).orElse(null);
        assertNotNull(saved);
        // Load the associations
        var sets = repository.findSetsByExercise(saved.getExerciseId());
        assertEquals(exercise.getExerciseId(), saved.getExerciseId());
        assertEquals(exercise.getTarget(), saved.getTarget());
        assertEquals(exercise.getName(), saved.getName());
        assertEquals(exercise.getDescription(), saved.getDescription());
        assertEquals(sets, exercise.getSets());
    }
    @Test
    public void testSaveAndFindByDescription() {
        var exercise = RepoUtils.createExercise();
        repository.save(exercise);

        // Test custom-fetch methods
        var saved = repository.findExercisesByDescription("Biceps-focused and in the shortened position");
        assert !saved.isEmpty();

        // Load the associations
        var sets = repository.findSetsByExercise(saved.get(0).getExerciseId());
        assertEquals(exercise.getExerciseId(), saved.get(0).getExerciseId());
        assertEquals(exercise.getTarget(), saved.get(0).getTarget());
        assertEquals(exercise.getName(), saved.get(0).getName());
        assertEquals(exercise.getDescription(), saved.get(0).getDescription());
        assertEquals(sets, exercise.getSets());
    }

    @Test
    public void testSaveAndFindByTarget() {
        var exercise = RepoUtils.createExercise();
        repository.save(exercise);

        var saved = repository.findExercisesByTarget("Biceps");
        assert !saved.isEmpty();

        // Load the associations
        var sets = repository.findSetsByExercise(saved.get(0).getExerciseId());
        assertEquals(exercise.getExerciseId(), saved.get(0).getExerciseId());
        assertEquals(exercise.getTarget(), saved.get(0).getTarget());
        assertEquals(exercise.getName(), saved.get(0).getName());
        assertEquals(exercise.getDescription(), saved.get(0).getDescription());
        assertEquals(sets, exercise.getSets());
    }

    @Test
    public void testSaveAndFindByName() {
        var exercise = RepoUtils.createExercise();
        repository.save(exercise);

        var saved = repository.findExerciseByName("Preacher Curls").orElse(null);
        assertNotNull(saved);

        // Load the associations
        var sets = repository.findSetsByExercise(saved.getExerciseId());
        assertEquals(exercise.getExerciseId(), saved.getExerciseId());
        assertEquals(exercise.getTarget(), saved.getTarget());
        assertEquals(exercise.getName(), saved.getName());
        assertEquals(exercise.getDescription(), saved.getDescription());
        assertEquals(sets, exercise.getSets());
    }
}
