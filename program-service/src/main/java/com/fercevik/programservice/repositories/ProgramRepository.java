package com.fercevik.programservice.repositories;

import com.fercevik.programservice.models.Program;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findProgramsByOwnerId(UUID owner_id);

    /**
     * ProgramRepository methods for app users. They all require the ownerId parameter
     * to distinguish programs
     */
    @Query("SELECT p FROM programs p WHERE p.tags IN :tags AND p.ownerId = :ownerId")
    List<Program> findProgramsByTags(List<String> tags, UUID ownerId);

    @Query("SELECT p FROM programs p WHERE p.createdAt >= :time AND p.ownerId = :ownerId")
    List<Program> findProgramsByCreatedAt(Date time, UUID ownerId);

    @Query("SELECT p FROM programs p WHERE p.updatedAt >= :time AND p.ownerId = :ownerId")
    List<Program> findProgramsByUpdatedAt(Date time, UUID ownerId);

    @Query("SELECT p FROM programs p WHERE p.active = false AND p.ownerId = :ownerId")
    List<Program> findProgramsByInactive(UUID ownerId);

    @Query("SELECT p FROM programs p WHERE p.active = true AND p.ownerId = :ownerId")
    Program findProgramByActive(UUID ownerId);

    @Modifying
    @Transactional
    @Query("UPDATE programs p SET p.active = false WHERE p.active = true AND p.ownerId = :ownerId")
    void deactivateCurrentActiveProgram(UUID ownerId);

}
