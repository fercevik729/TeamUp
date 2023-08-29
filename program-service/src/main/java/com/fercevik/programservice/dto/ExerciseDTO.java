package com.fercevik.programservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseDTO implements Serializable {
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
