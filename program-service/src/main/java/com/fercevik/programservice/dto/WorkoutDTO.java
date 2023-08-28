package com.fercevik.programservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorkoutDTO {
    private Long workoutId;
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters long")
    private String name;
    @Size(min = 3, max = 256, message = "description must be between 3 and 256 characters long")
    private String description;
    private Date date;

    @NotEmpty(message = "exercises cannot be empty")
    private List<ExerciseDTO> exercises;
}
