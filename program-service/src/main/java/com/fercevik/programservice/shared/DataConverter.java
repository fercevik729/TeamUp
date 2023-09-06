package com.fercevik.programservice.shared;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.dao.Workout;
import com.fercevik.programservice.dto.ExerciseDTO;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.dto.SetDTO;
import com.fercevik.programservice.dto.WorkoutDTO;

import java.util.UUID;

/**
 * Utility class that converts DTO -> DAO and vice versa
 */
public class DataConverter {

    // Helper methods to convert from DTO -> DAO

    /**
     * Converts a SetDTO object into a Set Entity object
     *
     * @param dto SetDTO object
     * @return a Set Entity instance
     */
    public static Set convertSetFromDTO(SetDTO dto) {
        return Set.builder().setId(dto.getSetId()).weight(dto.getWeight()).duration(dto.getDuration()).reps(dto.getReps()).build();
    }

    /**
     * Converts an ExerciseDTO object into an Exercise Entity object
     *
     * @param dto ExerciseDTO object
     * @return an Exercise Entity instance
     */
    public static Exercise convertExerciseFromDTO(ExerciseDTO dto) {
        return Exercise.builder().exerciseId(dto.getExerciseId()).name(dto.getName()).description(dto.getDescription())
                .target(dto.getTarget()).sets(dto.getSets().stream().map(DataConverter::convertSetFromDTO).toList())
                .build();
    }

    /**
     * Converts a WorkoutDTO object into a Workout Entity object
     *
     * @param dto WorkoutDTO object
     * @return a Workout Entity instance
     */
    public static Workout convertWorkoutFromDTO(WorkoutDTO dto) {
        return Workout.builder().workoutId(dto.getWorkoutId()).date(dto.getDate()).name(dto.getName())
                .description(dto.getDescription())
                .exercises(dto.getExercises().stream().map(DataConverter::convertExerciseFromDTO).toList()).build();
    }

    /**
     * Converts a ProgramDTO object into a Program Entity object
     *
     * @param ownerId uuid of the user who owns the program
     * @param dto     ProgramDTO object
     * @return a Program Entity instance
     */
    public static Program convertProgramFromDTO(UUID ownerId, ProgramDTO dto) {
        return Program.builder().programId(dto.getProgramId()).ownerId(ownerId).name(dto.getName()).tags(dto.getTags())
                .units(dto.getUnits()).active(dto.isActive())
                .workouts(dto.getWorkouts().stream().map(DataConverter::convertWorkoutFromDTO).toList()).build();
    }

    // Helper methods to convert from DAO -> DTO objects

    /**
     * Converts a Set instance to a SetDTO instance
     *
     * @param set Set entity object
     * @return a new SetDTO object
     */
    public static SetDTO convertDTOFromSet(Set set) {
        return SetDTO.builder().setId(set.getSetId()).weight(set.getWeight()).duration(set.getDuration())
                .reps(set.getReps()).build();
    }

    /**
     * Converts an Exercise instance to an ExerciseDTO instance
     *
     * @param exercise Exercise entity object
     * @return a new ExerciseDTO object
     */
    public static ExerciseDTO convertDTOFromExercise(Exercise exercise) {
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
    public static WorkoutDTO convertDTOFromWorkout(Workout workout) {
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
