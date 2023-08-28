package com.fercevik.programservice.dto;

import com.fercevik.programservice.shared.WeightUnits;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProgramDTO {
    private Long programId;
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters long")
    private String name;
    private boolean active = false;
    private List<String> tags;

    private WeightUnits units = WeightUnits.POUNDS;

    @Valid
    @NotEmpty(message = "workouts cannot be empty")
    private List<WorkoutDTO> workouts = new ArrayList<>();
}
