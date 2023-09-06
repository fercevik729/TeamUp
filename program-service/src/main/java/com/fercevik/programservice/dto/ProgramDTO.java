package com.fercevik.programservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fercevik.programservice.shared.WeightUnits;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramDTO implements Serializable {
    private Long programId;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters long")
    private String name;

    @Builder.Default
    private boolean active = false;

    private List<String> tags;

    @Builder.Default
    private WeightUnits units = WeightUnits.POUNDS;

    @Valid
    @NotEmpty(message = "workouts cannot be empty")
    private List<WorkoutDTO> workouts;
}
