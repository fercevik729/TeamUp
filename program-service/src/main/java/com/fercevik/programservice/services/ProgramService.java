package com.fercevik.programservice.services;

import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.exceptions.ActiveProgramNotFoundException;
import com.fercevik.programservice.exceptions.ProgramAlreadyExistsException;
import com.fercevik.programservice.exceptions.ProgramNotFoundException;
import com.fercevik.programservice.repositories.ProgramRepository;
import com.fercevik.programservice.shared.DataConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * Retrieves the currently active program if it exists for a user
     *
     * @param ownerId UUID of the owner creating the program
     * @return ProgramDTO instance containing the program data
     */
    public ProgramDTO getActiveProgram(UUID ownerId) {
        return programRepository.findProgramByActive(ownerId).map(DataConverter::convertDTOFromProgram).orElseThrow(
                () -> new ActiveProgramNotFoundException(
                        "no active program could be found for user"));
    }

    /**
     * Retrieves a program if one exists for a user with the specified program id
     *
     * @param ownerId UUID of the owner creating the program
     * @param programId program id
     * @return ProgramDTO instance containing the program data
     */
    public ProgramDTO getProgram(UUID ownerId, Long programId) {
        return programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId)
                .map(DataConverter::convertDTOFromProgram).orElseThrow(() -> new ProgramNotFoundException(
                        "program with id: " + programId + " and ownerId: " + ownerId + " could not be found"));
    }

    /**
     * Creates a new program if one with the same id or name doesn't already exist
     *
     * @param ownerId UUID of the owner creating the program
     * @param dto data transfer object containing the Program
     * @return new program's id
     */
    public long createProgram(UUID ownerId, ProgramDTO dto) {
        var program = DataConverter.convertProgramFromDTO(ownerId, dto);
        // Check id's
        Optional<Program> res = programRepository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId());
        if (res.isPresent())
            throw new ProgramAlreadyExistsException("program with the same id already exists");

        // Check name
        res = programRepository.findProgramByName(ownerId, program.getName());
        if (res.isPresent())
            throw new ProgramAlreadyExistsException("program with the same name already exists for this user");

        // Create the program
        programRepository.saveAndFlush(program);
        Program saved = programRepository.findProgramByName(ownerId, program.getName()).orElse(null);
        assert saved != null;
        return saved.getProgramId();
    }

    /**
     * Deletes a program owned by a particular user with the specified program id
     * @param ownerId UUID of the owner creating the program
     * @param programId program id
     * @return rows affected
     */
    public int deleteProgramById(UUID ownerId, Long programId) {
        return programRepository.deleteProgramByOwnerIdAndProgramId(ownerId, programId);
    }

}
