package com.fercevik.programservice.shared;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.dao.Workout;
import com.fercevik.programservice.dto.ExerciseDTO;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.dto.SetDTO;
import com.fercevik.programservice.dto.WorkoutDTO;

import java.util.List;
import java.util.UUID;

/**
 * Utility class that converts DTO -> DAO and vice versa
 */
public class DataConverter {

    // Helper methods to convert from DTO -> DAO
    /**
     * Converts a SetDTO object into a Set Entity object
     *
     * @param dto      SetDTO object
     * @param exercise Exercise that the SetDTO is associated with
     * @return a Set Entity instance
     */
    private static Set convertSetFromDTO(SetDTO dto, Exercise exercise) {
        return Set.builder().weight(dto.getWeight()).duration(dto.getDuration()).reps(dto.getReps()).exercise(exercise)
                .build();
    }

    /**
     * Converts an ExerciseDTO object into an Exercise Entity object
     *
     * @param dto     ExerciseDTO object
     * @param workout Workout that the ExerciseDTO is associated with
     * @return an Exercise Entity instance
     */
    private static Exercise convertExerciseFromDTO(ExerciseDTO dto, Workout workout) {
        Exercise exercise = new Exercise();
        exercise.setName(dto.getName());
        exercise.setTarget(dto.getTarget());
        exercise.setDescription(dto.getDescription());

        // Setup associations
        exercise.setWorkout(workout);
        List<Set> sets = dto.getSets().stream().map(set -> convertSetFromDTO(set, exercise)).toList();
        exercise.setSets(sets);

        return exercise;
    }

    /**
     * Converts a WorkoutDTO object into a Workout Entity object
     *
     * @param dto     WorkoutDTO object
     * @param program Program that the WorkoutDTO is associated with
     * @return a Workout Entity instance
     */
    private static Workout convertWorkoutFromDTO(WorkoutDTO dto, Program program) {
        Workout workout = new Workout();
        workout.setWorkoutId(dto.getWorkoutId());
        workout.setDate(dto.getDate());
        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());

        // Setup associations
        workout.setProgram(program);
        List<Exercise> exercises = dto.getExercises().stream()
                .map(exercise -> convertExerciseFromDTO(exercise, workout)).toList();
        workout.setExercises(exercises);
        return workout;
    }

    /**
     * Converts a ProgramDTO object into a Program Entity object
     *
     * @param ownerId uuid of the user who owns the program
     * @param dto     ProgramDTO object
     * @return a Program Entity instance
     */
    public static Program convertProgramFromDTO(UUID ownerId, ProgramDTO dto) {
        Program program = new Program();
        program.setProgramId(dto.getProgramId());
        program.setOwnerId(ownerId);
        program.setName(dto.getName());
        program.setTags(dto.getTags());
        program.setActive(dto.isActive());

        // Setup association
        List<Workout> workouts = dto.getWorkouts().stream().map(workout -> convertWorkoutFromDTO(workout, program))
                .toList();
        program.setWorkouts(workouts);
        return program;
    }

    // Helper methods to convert from DAO -> DTO objects

    /**
     * Converts a Set instance to a SetDTO instance
     *
     * @param set Set entity object
     * @return a new SetDTO object
     */
    private static SetDTO convertDTOFromSet(Set set) {
        return SetDTO.builder().setId(set.getSetId()).weight(set.getWeight()).duration(set.getDuration())
                .reps(set.getReps()).build();
    }

    /**
     * Converts an Exercise instance to an ExerciseDTO instance
     *
     * @param exercise Exercise entity object
     * @return a new ExerciseDTO object
     */
    private static ExerciseDTO convertDTOFromExercise(Exercise exercise) {
        return ExerciseDTO.builder().exerciseId(exercise.getExerciseId()).description(exercise.getDescription())
                .name(exercise.getName()).target(exercise.getTarget())
                .sets(exercise.getSets().stream().map(DataConverter::convertDTOFromSet).toList()).build();
    }

    /**
     * Converts a Workout instance to a WorkoutDTO instance
     *
     * @param workout Workout entity object
     * @return a new WorkoutDTO object
     */
    private static WorkoutDTO convertDTOFromWorkout(Workout workout) {
        return WorkoutDTO.builder().workoutId(workout.getWorkoutId()).description(workout.getDescription())
                .name(workout.getName()).date(workout.getDate())
                .exercises(workout.getExercises().stream().map(DataConverter::convertDTOFromExercise).toList()).build();
    }

    /**
     * Converts a Program instance to a ProgramDTO instance
     *
     * @param program Program entity object
     * @return a new ProgramDTO object
     */
    public static ProgramDTO convertDTOFromProgram(Program program) {
        return ProgramDTO.builder().programId(program.getProgramId()).units(program.getUnits()).name(program.getName())
                .tags(program.getTags()).active(program.isActive())
                .workouts(program.getWorkouts().stream().map(DataConverter::convertDTOFromWorkout).toList()).build();
    }
}
