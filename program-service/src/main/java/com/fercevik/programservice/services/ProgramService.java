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
import com.fercevik.programservice.repositories.WorkoutRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Service
public class ProgramService {
    private final ProgramRepository programRepository;
    private final WorkoutRepository workoutRepository;

    public List<Program> getUserPrograms(UUID ownerId) {
        return programRepository.findProgramsByOwnerId(ownerId);
    }

    public Program getActiveProgram(UUID ownerId) {
        return programRepository.findProgramByActive(ownerId);
    }

    public Program getProgram(UUID ownerId, Long programId) {
        return programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId);
    }

    public Program save(UUID userId, ProgramDTO dto) {
        var program = convertProgramFromDTO(userId, dto);
        // TODO: setup associations between objects
        return programRepository.save(program);
    }

    // Private methods to convert from DTO to DAO objects

    /**
     * Converts a SetDTO object into a Set Entity object
     *
     * @param dto SetDTO object
     * @return a Set Entity instance
     */
    private Set convertSetFromDTO(SetDTO dto) {
        return Set.builder().setId(dto.getSetId()).weight(dto.getWeight()).duration(dto.getDuration())
                .reps(dto.getReps()).setNumber(dto.getSetNumber()).build();
    }

    /**
     * Converts an ExerciseDTO object into an Exercise Entity object
     *
     * @param dto ExerciseDTO object
     * @return an Exercise Entity instance
     */
    private Exercise convertExerciseFromDTO(ExerciseDTO dto) {
        return Exercise.builder().exerciseId(dto.getExerciseId()).name(dto.getName()).target(dto.getTarget())
                .description(dto.getDescription())
                .sets(dto.getSets().stream().map(this::convertSetFromDTO).collect(Collectors.toList())).build();
    }

    /**
     * Converts a WorkoutDTO object into a Workout Entity object
     *
     * @param dto WorkoutDTO object
     * @return a Workout Entity instance
     */
    private Workout convertWorkoutFromDTO(WorkoutDTO dto) {
        return Workout.builder().workoutId(dto.getWorkoutId()).date(dto.getDate()).description(dto.getDescription())
                .exercises(dto.getExercises().stream().map(this::convertExerciseFromDTO).collect(Collectors.toList()))
                .name(dto.getName()).build();
    }

    /**
     * Converts a ProgramDTO object into a Program Entity object
     *
     * @param dto ProgramDTO object
     * @return a Program Entity instance
     */
    private Program convertProgramFromDTO(UUID ownerId, ProgramDTO dto) {
        return Program.builder().ownerId(ownerId).programId(dto.getProgramId()).name(dto.getName()).tags(dto.getTags())
                .active(dto.isActive())
                .workouts(dto.getWorkouts().stream().map(this::convertWorkoutFromDTO).collect(Collectors.toList()))
                .build();
    }
}
