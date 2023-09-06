package com.fercevik.programservice.shared;

import com.fercevik.programservice.utils.ConverterUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataConverterTests {
    @Test
    public void testConvertSetFromDTO() {
        var setDTO = ConverterUtils.createSetDTO();
        var expDAO = ConverterUtils.createSetDAO();
        var actDAO = DataConverter.convertSetFromDTO(setDTO);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromSet() {
        var setDAO = ConverterUtils.createSetDAO();
        var expDTO = ConverterUtils.createSetDTO();
        var actDTO = DataConverter.convertDTOFromSet(setDAO);
        assertEquals(expDTO, actDTO);
    }

    @Test
    public void testConvertExerciseFromDTO() {
        var exerciseDTO = ConverterUtils.createExerciseDTO();
        var expDAO = ConverterUtils.createExerciseDAO();
        var actDAO = DataConverter.convertExerciseFromDTO(exerciseDTO);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromExercise() {
        var dao = ConverterUtils.createExerciseDAO();
        var expDTO = ConverterUtils.createExerciseDTO();
        var actDTO = DataConverter.convertDTOFromExercise(dao);
        assertEquals(expDTO, actDTO);
    }

    @Test
    public void testConvertWorkoutFromDTO() {
        var dto = ConverterUtils.createWorkoutDTO();
        var expDAO = ConverterUtils.createWorkoutDAO();
        var actDAO = DataConverter.convertWorkoutFromDTO(dto);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromWorkout() {
        var dao = ConverterUtils.createWorkoutDAO();
        var expDTO = ConverterUtils.createWorkoutDTO();
        var actDTO = DataConverter.convertDTOFromWorkout(dao);
        assertEquals(expDTO, actDTO);
    }

    @Test
    public void testConvertProgramFromDTO() {
        var programDTO = ConverterUtils.createProgramDTO();
        var ownerId = UUID.randomUUID();
        var expDAO = ConverterUtils.createProgramDAO(ownerId);
        var actDAO = DataConverter.convertProgramFromDTO(ownerId, programDTO);
        assertEquals(expDAO, actDAO);
    }

    @Test
    public void testConvertDTOFromProgram() {
        var dao = ConverterUtils.createProgramDAO(UUID.randomUUID());
        var expDTO = ConverterUtils.createProgramDTO();
        var actDTO = DataConverter.convertDTOFromProgram(dao);
        assertEquals(expDTO, actDTO);
    }
}
