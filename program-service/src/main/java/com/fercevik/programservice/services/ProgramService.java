package com.fercevik.programservice.services;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.dao.Workout;
import com.fercevik.programservice.dto.ExerciseDTO;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.dto.SetDTO;
import com.fercevik.programservice.dto.WorkoutDTO;
import com.fercevik.programservice.repositories.ProgramRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class ProgramService {
    private final ProgramRepository programRepository;

    public List<ProgramDTO> getUserPrograms(UUID ownerId) {
        return programRepository.findProgramsByOwnerId(ownerId).stream().map(this::convertDTOFromProgram).toList();
    }

    public ProgramDTO getActiveProgram(UUID ownerId) {
        return programRepository.findProgramByActive(ownerId).map(this::convertDTOFromProgram).orElse(null);
    }

    public ProgramDTO getProgram(UUID ownerId, Long programId) {
        return programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId).map(this::convertDTOFromProgram)
                .orElse(null);
    }

    public Program save(UUID userId, ProgramDTO dto) {
        var program = convertProgramFromDTO(userId, dto);
        return programRepository.save(program);
    }

    // Helper methods to convert from DTO to DAO objects
    /**
     * Converts a SetDTO object into a Set Entity object
     *
     * @param dto SetDTO object
     * @param exercise Exercise that the SetDTO is associated with
     * @return a Set Entity instance
     */
    private Set convertSetFromDTO(SetDTO dto, Exercise exercise) {
        return Set.builder().setId(dto.getSetId()).weight(dto.getWeight()).duration(dto.getDuration())
                .reps(dto.getReps()).setNumber(dto.getSetNumber()).exercise(exercise).build();
    }

    /**
     * Converts an ExerciseDTO object into an Exercise Entity object
     *
     * @param dto ExerciseDTO object
     * @param workout Workout that the ExerciseDTO is associated with
     * @return an Exercise Entity instance
     */
    private Exercise convertExerciseFromDTO(ExerciseDTO dto, Workout workout) {
        Exercise exercise = new Exercise();
        exercise.setExerciseId(dto.getExerciseId());
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
     * @param dto WorkoutDTO object
     * @param program Program that the WorkoutDTO is associated with
     * @return a Workout Entity instance
     */
    private Workout convertWorkoutFromDTO(WorkoutDTO dto, Program program) {
        Workout workout = new Workout();
        workout.setWorkoutId(dto.getWorkoutId());
        workout.setDate(dto.getDate());
        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());

        // Setup associations
        workout.setProgram(program);
        List<Exercise> exercises = dto.getExercises().stream().map(exercise -> convertExerciseFromDTO(exercise, workout)).toList();
        workout.setExercises(exercises);
        return workout;
    }

    /**
     * Converts a ProgramDTO object into a Program Entity object
     *
     * @param ownerId uuid of the user who owns the program
     * @param dto ProgramDTO object
     * @return a Program Entity instance
     */
    private Program convertProgramFromDTO(UUID ownerId, ProgramDTO dto) {
        Program program = new Program();
        program.setOwnerId(ownerId);
        program.setProgramId(dto.getProgramId());
        program.setName(dto.getName());
        program.setTags(dto.getTags());
        program.setActive(dto.isActive());

        // Setup association
        List<Workout> workouts = dto.getWorkouts().stream().map(workout -> convertWorkoutFromDTO(workout, program)).toList();
        program.setWorkouts(workouts);
        return program;
    }

    // Helper methods to convert from DAO to DTO objects
    private SetDTO convertDTOFromSet(Set set) {
        return SetDTO.builder().setId(set.getSetId()).weight(set.getWeight()).setNumber(set.getSetNumber())
                .duration(set.getDuration()).reps(set.getReps()).build();
    }
    private ExerciseDTO convertDTOFromExercise(Exercise exercise) {
        return ExerciseDTO.builder().exerciseId(exercise.getExerciseId()).description(exercise.getDescription())
                .name(exercise.getName()).target(exercise.getTarget()).sets(exercise.getSets().stream()
                        .map(this::convertDTOFromSet).toList()).build();
    }
    private WorkoutDTO convertDTOFromWorkout(Workout workout) {
        return WorkoutDTO.builder().workoutId(workout.getWorkoutId()).description(workout.getDescription()).name(
                workout.getName()).date(workout.getDate()).exercises(workout.getExercises().stream()
                .map(this::convertDTOFromExercise).toList()).build();
    }
    private ProgramDTO convertDTOFromProgram(Program program) {
        return ProgramDTO.builder().programId(program.getProgramId()).units(program.getUnits()).name(program.getName())
                .tags(program.getTags()).active(program.isActive()).workouts(program.getWorkouts().stream()
                        .map(this::convertDTOFromWorkout).toList()).build();
    }
}
