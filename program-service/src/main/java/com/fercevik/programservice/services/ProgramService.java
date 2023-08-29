package com.fercevik.programservice.services;

import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.exceptions.ActiveProgramNotFoundException;
import com.fercevik.programservice.exceptions.ProgramNotFoundException;
import com.fercevik.programservice.repositories.ProgramRepository;
import com.fercevik.programservice.shared.DataConverter;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Data
@Service
public class ProgramService {
    private final ProgramRepository programRepository;

    public List<ProgramDTO> getUserPrograms(UUID ownerId) {
        return programRepository.findProgramsByOwnerId(ownerId).stream().map(DataConverter::convertDTOFromProgram)
                .toList();
    }

    public ProgramDTO getActiveProgram(UUID ownerId) {
        return programRepository.findProgramByActive(ownerId).map(DataConverter::convertDTOFromProgram).orElseThrow(
                () -> new ActiveProgramNotFoundException(
                        "no active program could be found for user with id: " + ownerId));
    }

    public ProgramDTO getProgram(UUID ownerId, Long programId) {
        return programRepository.findProgramByOwnerIdAndProgramId(ownerId, programId)
                .map(DataConverter::convertDTOFromProgram).orElseThrow(() -> new ProgramNotFoundException(
                        "program with id: " + programId + " and ownerId: " + ownerId + " could not be found"));


    }

    public Program save(UUID userId, ProgramDTO dto) {
        var program = DataConverter.convertProgramFromDTO(userId, dto);
        return programRepository.save(program);
    }

}
