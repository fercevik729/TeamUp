package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.repositories.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExerciseRepositoryTests {

    @Autowired
    private ExerciseRepository repository;

    @Test
    @Transactional
    public void testSaveAndRetrieve() {
        var exercise = Exercise.builder().description("Biceps-focused and in the shortened position").target("Biceps")
                .name("Preacher curls").build();
        repository.save(exercise);

        var saved1 = repository.findById(exercise.getExerciseId()).orElse(null);
        assertNotNull(saved1);
        // Eagerly load the associations
        saved1.getSets().size();
        assertEquals(exercise, saved1);

        // Test custom-fetch methods
        var saved2 = repository.findExercisesByDescription("Biceps-focused and in the shortened position");
        assert !saved2.isEmpty();
        assertEquals(exercise, saved2.get(0));

        var saved3 = repository.findExercisesByTarget("Biceps");
        assert !saved3.isEmpty();
        assertEquals(exercise, saved3.get(0));

        var saved4 = repository.findSetsByExercise(exercise.getExerciseId());
        assert saved4.isEmpty();

        var saved5 = repository.findExerciseByName("Preacher curls").orElse(null);
        assertNotNull(saved5);
        assertEquals(exercise, saved5);
    }
}
