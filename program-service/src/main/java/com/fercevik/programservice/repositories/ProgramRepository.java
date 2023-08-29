package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Program;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    /**
     * ProgramRepository methods for app users. They all require the ownerId parameter
     * to distinguish programs
     */
    // READ Queries
    List<Program> findProgramsByOwnerId(UUID ownerId);

    @Query("SELECT p FROM programs p WHERE p.programId = :programId AND p.ownerId = :ownerId")
    Optional<Program> findProgramByOwnerIdAndProgramId(UUID ownerId, Long programId);

    @Query("SELECT p FROM programs p WHERE p.name LIKE :name AND p.ownerId = :ownerId")
    Optional<Program> findProgramByName(UUID ownerId, String name);

    @Query("SELECT p FROM programs p WHERE p.tags IN :tags AND p.ownerId = :ownerId")
    List<Program> findProgramsByTags(UUID ownerId, List<String> tags);

    @Query("SELECT p FROM programs p WHERE p.createdAt >= :time AND p.ownerId = :ownerId")
    List<Program> findProgramsByCreatedAt(UUID ownerId, Date time);

    @Query("SELECT p FROM programs p WHERE p.updatedAt >= :time AND p.ownerId = :ownerId")
    List<Program> findProgramsByUpdatedAt(UUID ownerId, Date time);

    @Query("SELECT p FROM programs p WHERE p.active = false AND p.ownerId = :ownerId")
    List<Program> findProgramsByInactive(UUID ownerId);

    @Query("SELECT p FROM programs p WHERE p.active = true AND p.ownerId = :ownerId")
    Optional<Program> findProgramByActive(UUID ownerId);

    // UPDATE Queries
    @Modifying
    @Transactional
    @Query("UPDATE programs p SET p.active = false WHERE p.active = true AND p.ownerId = :ownerId")
    void deactivateCurrentActiveProgram(UUID ownerId);


    // DELETE Queries
    @Modifying
    @Transactional
    @Query("DELETE FROM programs p WHERE p.programId = :programId AND p.ownerId = :ownerId")
    void deleteProgramByOwnerIdAndProgramId(UUID ownerId, Long programId);

}
