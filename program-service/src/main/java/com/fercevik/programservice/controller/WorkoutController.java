package com.fercevik.programservice.controller;

import com.fercevik.programservice.annotations.User;
import com.fercevik.programservice.dto.WorkoutDTO;
import com.fercevik.programservice.services.ProgramService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Data
@Slf4j
@RequestMapping("/programs/{programId}/workouts")
public class WorkoutController {

    private final ProgramService programService;

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> allWorkouts(@PathVariable long programId, @User String userId) {
        var workouts = programService.getAllWorkoutsForProgram(UUID.fromString(userId), programId);
        return ResponseEntity.ok(workouts);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable long programId, @PathVariable long workoutId, @User String userId) {
        // Call workout service

        return ResponseEntity.noContent().build();
    }

}
