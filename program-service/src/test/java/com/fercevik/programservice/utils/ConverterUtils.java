package com.fercevik.programservice.utils;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.dao.Workout;
import com.fercevik.programservice.dto.ExerciseDTO;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.dto.SetDTO;
import com.fercevik.programservice.dto.WorkoutDTO;
import com.fercevik.programservice.shared.WeightUnits;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ConverterUtils {
    public static SetDTO createSetDTO() {
        return SetDTO.builder().setId(1).duration(Duration.ofSeconds(20)).weight(15).reps(12).build();
    }

    public static Set createSetDAO() {
        return Set.builder().setId(1).duration(Duration.ofSeconds(20)).weight(15).reps(12).build();
    }

    public static ExerciseDTO createExerciseDTO() {
        return ExerciseDTO.builder().exerciseId(1).name("DB Curls").description("Basic biceps exercise using dumbbells")
                .target("Biceps").sets(List.of(ConverterUtils.createSetDTO())).build();
    }

    public static Exercise createExerciseDAO() {
        return Exercise.builder().exerciseId(1).name("DB Curls").description("Basic biceps exercise using dumbbells")
                .target("Biceps").sets(List.of(ConverterUtils.createSetDAO())).build();
    }

    public static WorkoutDTO createWorkoutDTO() {
        return WorkoutDTO.builder().workoutId(1).date(LocalDate.now()).exercises(List.of(createExerciseDTO())).name("Upper Day 1")
                .description("Biceps and Chest Exercises").build();
    }

    public static Workout createWorkoutDAO() {
        return Workout.builder().workoutId(1).date(LocalDate.now()).exercises(List.of(createExerciseDAO())).name("Upper Day 1")
                .description("Biceps and Chest Exercises").build();
    }

    public static ProgramDTO createProgramDTO() {
        return ProgramDTO.builder().programId(1).active(true).tags(List.of("Fitness", "Beginner"))
                .units(WeightUnits.KILOGRAMS).name("Program for Beginner Clients")
                .workouts(List.of(ConverterUtils.createWorkoutDTO())).build();
    }

    public static Program createProgramDAO(UUID ownerId) {
        return Program.builder().programId(1).active(true).tags(List.of("Fitness", "Beginner"))
                .ownerId(ownerId).workouts(List.of(ConverterUtils.createWorkoutDAO())).units(WeightUnits.KILOGRAMS)
                .name("Program for Beginner Clients").build();
    }
}
