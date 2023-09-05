package com.fercevik.programservice.repositories;

import com.fercevik.programservice.utils.RepoUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProgramRepositoryTests {

    @Autowired
    private ProgramRepository repository;

    @Test
    public void testSaveAndFind() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
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

    @Test
    public void testSaveAndFindByName() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        repository.save(program);

        var saved = repository.findProgramByName(ownerId, program.getName()).orElse(null);
        assertNotNull(saved);
        assertEquals(saved.getProgramId(), program.getProgramId());
        assertEquals(saved.getTags().stream().toList(), program.getTags());
        assertEquals(saved.isActive(), program.isActive());
    }

    @Test
    public void testSaveAndFindByActive() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        repository.save(program);

        var saved = repository.findProgramByActive(ownerId).orElse(null);
        assertNotNull(saved);
        assertEquals(saved.getProgramId(), program.getProgramId());
        assertEquals(saved.getTags().stream().toList(), program.getTags());
        assertEquals(saved.isActive(), program.isActive());
        assertEquals(saved.getName(), program.getName());

        var empty = repository.findProgramsByInactive(ownerId);
        assert empty.isEmpty();
    }

    @Test
    public void testSaveAndFindByTags() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        repository.save(program);

        var saved = repository.findProgramsByOwnerIdAndTagsIn(ownerId, List.of("Bodybuilding", "Soccer"));
        assert !saved.isEmpty();
        assertEquals(saved.get(0).getProgramId(), program.getProgramId());
        assertEquals(saved.get(0).isActive(), program.isActive());
        assertEquals(saved.get(0).getName(), program.getName());

        var empty = repository.findProgramsByOwnerIdAndTagsIn(ownerId, List.of("Soccer"));
        assert empty.isEmpty();
    }

    @Test
    public void testSaveAndFindByCreatedAt() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        var createdDate = LocalDate.now();
        repository.save(program);

        var saved = repository.findProgramsByCreatedAt(ownerId, createdDate);
        assert !saved.isEmpty();

        assertEquals(saved.get(0).getProgramId(), program.getProgramId());
        assertEquals(saved.get(0).getTags().stream().toList(), program.getTags());
        assertEquals(saved.get(0).isActive(), program.isActive());
        assertEquals(saved.get(0).getName(), program.getName());

        var futureDate = createdDate.plusDays(1);
        var saved2 = repository.findProgramsByCreatedAt(ownerId, futureDate);
        assert saved2.isEmpty();
    }

    @Test
    public void testSaveAndFindByUpdatedAt() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        var updatedDate = LocalDateTime.now();
        repository.save(program);

        var saved = repository.findProgramsByUpdatedAt(ownerId, updatedDate);
        assert !saved.isEmpty();

        assertEquals(saved.get(0).getProgramId(), program.getProgramId());
        assertEquals(saved.get(0).getTags().stream().toList(), program.getTags());
        assertEquals(saved.get(0).isActive(), program.isActive());
        assertEquals(saved.get(0).getName(), program.getName());

        var futureDate = updatedDate.plusDays(1);
        var saved2 = repository.findProgramsByUpdatedAt(ownerId, futureDate);
        assert saved2.isEmpty();
    }

    @Test
    @Transactional
    public void testUpdate() {
       var program = RepoUtils.createProgram();
       var ownerId = program.getOwnerId();

       repository.save(program);
       String updatedName = "Second Program";
       program.setName(updatedName);
       repository.save(program);

       var updatedProgram = repository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId()).orElse(null);
       assertNotNull(updatedProgram);
       assertEquals(updatedProgram.getName(), updatedName);
    }

    @Test
    @Transactional
    public void testDeactivateProgram() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        repository.save(program);
        repository.deactivateCurrentActiveProgram(ownerId);

        var updatedProgram = repository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId()).orElse(null);
        assertNotNull(updatedProgram);
        assert !updatedProgram.isActive();
    }

    @Test
    @Transactional
    public void testDeleteProgram() {
        var program = RepoUtils.createProgram();
        var ownerId = program.getOwnerId();
        repository.save(program);
        repository.deleteProgramByOwnerIdAndProgramId(ownerId, program.getProgramId());

        var deletedProgram = repository.findProgramByOwnerIdAndProgramId(ownerId, program.getProgramId()).orElse(null);
        assertNull(deletedProgram);

    }
}
