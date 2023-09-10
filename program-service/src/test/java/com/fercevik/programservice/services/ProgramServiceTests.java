package com.fercevik.programservice.services;

import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.exceptions.ActiveProgramNotFoundException;
import com.fercevik.programservice.exceptions.ProgramAlreadyExistsException;
import com.fercevik.programservice.exceptions.ProgramNotFoundException;
import com.fercevik.programservice.repositories.ProgramRepository;
import com.fercevik.programservice.shared.DataConverter;
import com.fercevik.programservice.utils.RepoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTests {
    ProgramService programService;

    @Mock
    ProgramRepository repository;

    @BeforeEach
    void init() {
        programService = new ProgramService(repository);
    }

    @Test
    void testGetUserPrograms() {
        UUID ownerId = UUID.randomUUID();
        List<Program> data = List.of(RepoUtils.createProgramDAO(ownerId));

        Mockito.when(repository.findProgramsByOwnerId(ownerId)).thenReturn(data);
        List<ProgramDTO> actual = programService.getUserPrograms(ownerId);
        List<ProgramDTO> expected = data.stream().map(DataConverter::convertDTOFromProgram).toList();
        assertEquals(actual, expected);
    }

    @Test
    void testGetActiveProgram() {
        UUID ownerId = UUID.randomUUID();
        Program data = RepoUtils.createProgramDAO(ownerId);

        Mockito.when(repository.findProgramByActive(ownerId)).thenReturn(Optional.of(data));
        ProgramDTO actual = programService.getActiveProgram(ownerId);
        ProgramDTO expected = DataConverter.convertDTOFromProgram(data);
        assertEquals(actual, expected);
    }

    @Test
    void testGetActiveProgramThrows() {
        UUID ownerId = UUID.randomUUID();
        Program data = RepoUtils.createProgramDAO(ownerId);
        data.setActive(false);

        Mockito.when(repository.findProgramByActive(ownerId)).thenReturn(Optional.empty());
        assertThrows(ActiveProgramNotFoundException.class, () -> programService.getActiveProgram(ownerId));
    }

    @Test
    void testGetProgram() {
        UUID ownerId = UUID.randomUUID();
        Program data = RepoUtils.createProgramDAO(ownerId);
        Long id = data.getProgramId();

        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, id)).thenReturn(Optional.of(data));
        ProgramDTO actual = programService.getProgram(ownerId, id);
        ProgramDTO expected = DataConverter.convertDTOFromProgram(data);
        assertEquals(actual, expected);
    }

    @Test
    void testGetProgramThrows() {
        UUID ownerId = UUID.randomUUID();
        Program data = RepoUtils.createProgramDAO(ownerId);
        long id = data.getProgramId();

        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, id + 1)).thenReturn(Optional.empty());
        assertThrows(ProgramNotFoundException.class, () -> programService.getProgram(ownerId, id + 1));
    }

    @Test
    void testDeleteProgram() {
        UUID ownerId = UUID.randomUUID();
        long programId = 123232L;
        Mockito.when(repository.deleteProgramByOwnerIdAndProgramId(ownerId, programId)).thenReturn(1);
        assert programService.deleteProgramById(ownerId, programId) > 0;
    }

    @Test
    void testCreateProgram() {
        UUID ownerId = UUID.randomUUID();
        ProgramDTO data = RepoUtils.createProgramDTO();
        Program program = DataConverter.convertProgramFromDTO(ownerId, data);

        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, data.getProgramId()))
                .thenReturn(Optional.empty());
        Mockito.when(repository.findProgramByName(ownerId, data.getName())).thenReturn(Optional.empty());
        Mockito.when(repository.save(program)).thenReturn(program);
        long actual = programService.createProgram(ownerId, data);
        assertEquals(data.getProgramId(), actual);
    }

    @Test
    void testCreateProgramSameIdExists() {
        UUID ownerId = UUID.randomUUID();
        ProgramDTO data = RepoUtils.createProgramDTO();
        Program program = DataConverter.convertProgramFromDTO(ownerId, data);
        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, data.getProgramId()))
                .thenReturn(Optional.of(program));
        assertThrows(ProgramAlreadyExistsException.class, () -> programService.createProgram(ownerId, data));
    }

    @Test
    void testCreateProgramSameNameExists() {
        UUID ownerId = UUID.randomUUID();
        ProgramDTO data = RepoUtils.createProgramDTO();
        Program program = DataConverter.convertProgramFromDTO(ownerId, data);
        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, data.getProgramId()))
                .thenReturn(Optional.empty());
        Mockito.when(repository.findProgramByName(ownerId, data.getName())).thenReturn(Optional.of(program));
        assertThrows(ProgramAlreadyExistsException.class, () -> programService.createProgram(ownerId, data));
    }

    @Test
    void testUpdateProgram() {
        UUID ownerId = UUID.randomUUID();
        ProgramDTO data = RepoUtils.createProgramDTO();
        Program program = DataConverter.convertProgramFromDTO(ownerId, data);
        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId()))
                .thenReturn(Optional.of(program));
        Mockito.when(repository.save(program)).thenReturn(program);
        assertEquals(program.getProgramId(), programService.updateProgram(ownerId, data));
    }

    @Test
    void testUpdateProgramDoesntExist() {
        UUID ownerId = UUID.randomUUID();
        ProgramDTO data = RepoUtils.createProgramDTO();
        Program program = DataConverter.convertProgramFromDTO(ownerId, data);
        Mockito.when(repository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId()))
                .thenReturn(Optional.empty());
        assertThrows(ProgramNotFoundException.class, () -> programService.updateProgram(ownerId, data));
    }
}
