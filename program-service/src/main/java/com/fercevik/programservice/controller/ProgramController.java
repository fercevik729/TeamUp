package com.fercevik.programservice.controller;

import com.fercevik.programservice.annotations.User;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.services.ProgramService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Data
@Slf4j
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;


    // CRUD Operations for User programs
    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms(@User String userId) {
        // Get all the programs this user owns
        return ResponseEntity.ok(programService.getUserPrograms(UUID.fromString(userId)));

    }

    @PostMapping
    public ResponseEntity<String> createProgram(@Valid @RequestBody ProgramDTO newProgram,
                                                @User String userId) {

        // Save the user's program
        Long savedId = programService.createProgram(UUID.fromString(userId), newProgram);

        // Create response
        URI destination = UriComponentsBuilder.fromUriString("/programs/" + savedId.toString()).build().toUri();

        return ResponseEntity.created(destination).build();
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDTO> getProgram(@PathVariable Long programId, @User String userId) {
        return ResponseEntity.ok(programService.getProgram(UUID.fromString(userId), programId));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId, @User String userId) {
        programService.deleteProgramById(UUID.fromString(userId), programId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<ProgramDTO> getActiveProgram(@User String userId) {
        return ResponseEntity.ok(programService.getActiveProgram(UUID.fromString(userId)));
    }

    @GetMapping("/echo")
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("You have reached a secure endpoint.");
    }
}
