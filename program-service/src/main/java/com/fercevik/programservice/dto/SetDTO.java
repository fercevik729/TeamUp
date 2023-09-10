package com.fercevik.programservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetDTO implements Serializable {
    private long setId;

    @Min(value = 1, message = "Reps cannot be less than 1")
    private int reps;

    @Min(value = 1, message = "Weight cannot be less than 1")
    private double weight;

    private double duration;
}
