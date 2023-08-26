package com.fercevik.programservice.controller;

import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.services.OpaqueTokenService;
import com.fercevik.programservice.services.ProgramService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<List<Program>> getAllPrograms(BearerTokenAuthentication token) {
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Get all the programs this user owns
        UUID userId = opaqueTokenService.extractUserId(token);
        return ResponseEntity.ok(programService.getUserPrograms(userId));

    }

    @PostMapping
    public ResponseEntity<String> createProgram(@Valid @RequestBody ProgramDTO newProgram,
                                                BearerTokenAuthentication token, BindingResult bindingResult) {
        // Check authentication
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // Check DTO in request body
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body("Validation error");

        UUID userId = opaqueTokenService.extractUserId(token);
        Program saved = programService.save(userId, newProgram);

        URI destination = UriComponentsBuilder.fromUriString("/programs/" + saved.getProgramId().toString()).build()
                .toUri();
        return ResponseEntity.created(destination).build();

    }

    @GetMapping("/{programId}")
    public ResponseEntity<Program> getProgram(@PathVariable Long programId, BearerTokenAuthentication token) {
        if (!opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID userId = opaqueTokenService.extractUserId(token);
        return ResponseEntity.ok(programService.getProgram(userId, programId));
    }

    @GetMapping("/active")
    public ResponseEntity<Program> getActiveProgram(BearerTokenAuthentication token) {
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
