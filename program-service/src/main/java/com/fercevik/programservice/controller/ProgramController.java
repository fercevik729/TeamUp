package com.fercevik.programservice.controller;

import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.services.OpaqueTokenService;
import com.fercevik.programservice.services.ProgramService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
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
    private final OpaqueTokenService opaqueTokenService;
    private final ProgramService programService;


    // CRUD Operations for User programs
    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms(BearerTokenAuthentication token) {
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Get all the programs this user owns
        UUID userId = opaqueTokenService.extractUserId(token);
        return ResponseEntity.ok(programService.getUserPrograms(userId));

    }

    @PostMapping
    public ResponseEntity<String> createProgram(@Valid @RequestBody ProgramDTO newProgram,
                                                BearerTokenAuthentication token) {
        // Check authentication
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Save the user's program
        UUID userId = opaqueTokenService.extractUserId(token);
        Long savedId = programService.createProgram(userId, newProgram);

        // Create response
        URI destination = UriComponentsBuilder.fromUriString("/programs/" + savedId.toString()).build().toUri();

        return ResponseEntity.created(destination).build();
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDTO> getProgram(@PathVariable Long programId, BearerTokenAuthentication token) {
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID userId = opaqueTokenService.extractUserId(token);
        return ResponseEntity.ok(programService.getProgram(userId, programId));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId, BearerTokenAuthentication token) {
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID userId = opaqueTokenService.extractUserId(token);
        programService.deleteProgramById(userId, programId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<ProgramDTO> getActiveProgram(BearerTokenAuthentication token) {
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID user_id = opaqueTokenService.extractUserId(token);
        return ResponseEntity.ok(programService.getActiveProgram(user_id));

    }

    @GetMapping("/echo")
    public ResponseEntity<String> echo(BearerTokenAuthentication token) {
        log.info("Got request from: " + token.getName());
        if (opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE)) {
            return ResponseEntity.ok(
                    "Hello Sir " + opaqueTokenService.extractUserId(token) + ", you have reached a secure endpoint");
        } else if (opaqueTokenService.hasAuthority(token, KeycloakConstants.ADMIN_ROLE)) {
            return ResponseEntity.ok("HELLO MR. ADMIN");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
