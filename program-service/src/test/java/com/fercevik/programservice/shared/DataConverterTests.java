package com.fercevik.programservice.shared;

import com.fercevik.programservice.utils.RepoUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataConverterTests {

    @Test
    public void testConstructor() {
        assertThrows(AssertionError.class, DataConverter::new);
    }
    @Test
    public void testConvertSetFromDTO() {
        var setDTO = RepoUtils.createSetDTO();
        var expDAO = RepoUtils.createSetDAO();
        var actDAO = DataConverter.convertSetFromDTO(setDTO);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromSet() {
        var setDAO = RepoUtils.createSetDAO();
        var expDTO = RepoUtils.createSetDTO();
        var actDTO = DataConverter.convertDTOFromSet(setDAO);
        assertEquals(expDTO, actDTO);
    }

    @Test
    public void testConvertExerciseFromDTO() {
        var exerciseDTO = RepoUtils.createExerciseDTO();
        var expDAO = RepoUtils.createExerciseDAO();
        var actDAO = DataConverter.convertExerciseFromDTO(exerciseDTO);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromExercise() {
        var dao = RepoUtils.createExerciseDAO();
        var expDTO = RepoUtils.createExerciseDTO();
        var actDTO = DataConverter.convertDTOFromExercise(dao);
        assertEquals(expDTO, actDTO);
    }

    @Test
    public void testConvertWorkoutFromDTO() {
        var dto = RepoUtils.createWorkoutDTO();
        var expDAO = RepoUtils.createWorkoutDAO();
        var actDAO = DataConverter.convertWorkoutFromDTO(dto);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromWorkout() {
        var dao = RepoUtils.createWorkoutDAO();
        var expDTO = RepoUtils.createWorkoutDTO();
        var actDTO = DataConverter.convertDTOFromWorkout(dao);
        assertEquals(expDTO, actDTO);
    }

    @Test
    public void testConvertProgramFromDTO() {
        var programDTO = RepoUtils.createProgramDTO();
        var ownerId = UUID.randomUUID();
        var expDAO = RepoUtils.createProgramDAO(ownerId);
        var actDAO = DataConverter.convertProgramFromDTO(ownerId, programDTO);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromProgram() {
        var dao = RepoUtils.createProgramDAO(UUID.randomUUID());
        var expDTO = RepoUtils.createProgramDTO();
        var actDTO = DataConverter.convertDTOFromProgram(dao);
        assertEquals(expDTO, actDTO);
    }
}
