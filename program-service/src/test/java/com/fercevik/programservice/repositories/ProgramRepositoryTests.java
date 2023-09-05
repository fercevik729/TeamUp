package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.shared.WeightUnits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProgramRepositoryTests {

    @Autowired
    private ProgramRepository repository;

    @Test
    public void testSaveAndRetrieve() {
        UUID ownerId = UUID.randomUUID();
        Program program = Program.builder().tags(List.of("Bodybuilding", "Powerlifting")).active(true).ownerId(ownerId).name("First Program").build();
        repository.save(program);

        var saved = repository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId()).orElse(null);
        assertNotNull(saved);
        var workouts = repository.findWorkoutsForProgram(ownerId, program.getProgramId());
        assert workouts.isEmpty();

        assertEquals(saved.getProgramId(), program.getProgramId());
        assertEquals(saved.getTags().stream().toList(), program.getTags());
        assertEquals(saved.isActive(), program.isActive());
        assertEquals(saved.getName(), program.getName());
    }
}
