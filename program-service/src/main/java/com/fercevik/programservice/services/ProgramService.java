package com.fercevik.programservice.services;

import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Workout;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.dto.WorkoutDTO;
import com.fercevik.programservice.exceptions.ActiveProgramNotFoundException;
import com.fercevik.programservice.exceptions.ProgramAlreadyExistsException;
import com.fercevik.programservice.exceptions.ProgramNotFoundException;
import com.fercevik.programservice.exceptions.WorkoutNotFoundException;
import com.fercevik.programservice.repositories.ProgramRepository;
import com.fercevik.programservice.shared.DataConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
@Slf4j
public class ProgramService {
    private final ProgramRepository programRepository;

    /**
     * Retrieves all the programs for a user
     *
     * @param ownerId UUID of the owner creating the program
     * @return list of ProgramDTO instances containing the program data
     */
    public List<ProgramDTO> getUserPrograms(UUID ownerId) {
        return programRepository.findProgramsByOwnerId(ownerId).stream().map(DataConverter::convertDTOFromProgram)
                .toList();
    }

    /**
     * Retrieves all the workouts for a user
     *
     * @param ownerId   UUID of the owner creating the program
     * @param programId id of the program
     * @return list of WorkoutDTO instances containing the workout data
     */
    public List<WorkoutDTO> getAllWorkoutsForProgram(UUID ownerId, long programId) {
        List<Workout> results = programRepository.findWorkoutsForProgram(ownerId, programId);
        if (results.isEmpty()) throw new WorkoutNotFoundException("no workouts found for program of id: " + programId);
        return results.stream().map(DataConverter::convertDTOFromWorkout).toList();
    }

    /**
     * Retrieves the currently active program if it exists for a user
     *
     * @param ownerId UUID of the program owner
     * @return ProgramDTO instance containing the program data
     */
    public ProgramDTO getActiveProgram(UUID ownerId) {
        return programRepository.findProgramByActive(ownerId).map(DataConverter::convertDTOFromProgram)
                .orElseThrow(() -> new ActiveProgramNotFoundException("no active program could be found for user"));
    }

    /**
     * Retrieves a program if one exists for a user with the specified program id
     *
     * @param ownerId   UUID of the program owner
     * @param programId program id
     * @return ProgramDTO instance containing the program data
     */
    public ProgramDTO getProgram(UUID ownerId, Long programId) {
        return programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId)
                .map(DataConverter::convertDTOFromProgram).orElseThrow(() -> new ProgramNotFoundException(
                        "program with id: " + programId + " and ownerId: " + ownerId + " could not be found"));
    }

    /**
     * Retrieves a program by name if one exists
     *
     * @param ownerId UUID of the program owner
     * @param name    name of the target program
     * @return ProgramDTO instance that matches the name parameter
     */
    public ProgramDTO getProgramByName(UUID ownerId, String name) {
        return programRepository.findProgramByName(ownerId, name).map(DataConverter::convertDTOFromProgram).orElseThrow(
                () -> new ProgramNotFoundException(
                        "program with name: " + name + " and ownerId: " + ownerId + " could not be found"));
    }

    /**
     * Retrieves all programs that match an array of tags
     *
     * @param ownerId UUID of the programs' owner
     * @param tags    list of tags
     * @return list of ProgramDTO instances with matching tags
     */
    public List<ProgramDTO> getProgramsByTags(UUID ownerId, String[] tags) {
        return programRepository.findProgramsByOwnerIdAndTagsIn(ownerId, List.of(tags)).stream()
                .map(DataConverter::convertDTOFromProgram).toList();
    }

    /**
     * Sets current program as active if it isn't already. Deactivates previously active program
     *
     * @param ownerId   UUID of the program owner
     * @param programId id of the program to be set as active
     */
    public void activateProgram(UUID ownerId, long programId) {
        Program newActive = programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId)
                .orElseThrow(() -> new ProgramNotFoundException("program with id could not be found"));
        programRepository.deactivateCurrentActiveProgram(ownerId);
        newActive.setActive(true);
        programRepository.save(newActive);
    }

    /**
     * Creates a new program if one with the same id or name doesn't already exist
     *
     * @param ownerId UUID of the owner creating the program
     * @param dto     data transfer object containing the Program
     * @return new program's id
     */
    @Transactional
    public long createProgram(UUID ownerId, ProgramDTO dto) {
        var program = DataConverter.convertProgramFromDTO(ownerId, dto);
        // Check id's
        Optional<Program> res = programRepository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId());
        if (res.isPresent()) throw new ProgramAlreadyExistsException("program with the same id already exists");

        // Check name
        res = programRepository.findProgramByName(ownerId, program.getName());
        if (res.isPresent())
            throw new ProgramAlreadyExistsException("program with the same name already exists for this user");

        // Create the program
        Program saved = programRepository.save(program);
        return saved.getProgramId();
    }

    /**
     * Updates a program with new data
     *
     * @param ownerId UUID of the program owner
     * @param dto     new program
     * @return id of the program that wasn't updated
     */
    @Transactional
    public long updateProgram(UUID ownerId, ProgramDTO dto) {
        var program = DataConverter.convertProgramFromDTO(ownerId, dto);
        var programId = program.getProgramId();
        // Check if that program exists
        Optional<Program> res = programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId);
        if (res.isEmpty()) throw new ProgramNotFoundException("program with id=" + programId + " doesn't exist");
        // Update it if it does
        return programRepository.save(program).getProgramId();

    }

    /**
     * Deletes a program owned by a particular user with the specified program id
     *
     * @param ownerId   UUID of the owner creating the program
     * @param programId program id
     * @return rows affected
     */
    public int deleteProgramById(UUID ownerId, Long programId) {
        return programRepository.deleteProgramByOwnerIdAndProgramId(ownerId, programId);
    }

}
