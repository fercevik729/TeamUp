package com.fercevik.programservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseDTO {
    private Long exerciseId;
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters long")
    private String name;
    @Size(min = 3, max = 256, message = "description must be between 3 and 256 characters long")
    private String description;
    @Size(min = 3, max = 50, message = "target must be between 3 and 50 characters long")
    private String target;

    @Valid
    @NotEmpty(message = "sets cannot be empty")
    private List<SetDTO> sets;
}
