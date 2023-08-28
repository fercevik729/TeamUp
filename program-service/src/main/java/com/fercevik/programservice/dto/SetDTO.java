package com.fercevik.programservice.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.Duration;

@Data
public class SetDTO {
    private Long setId;
    @Min(value = 1, message = "Set number cannot be less than 1")
    private int setNumber;
    @Min(value = 1, message = "Reps cannot be less than 1")
    private int reps;
    @Min(value = 1, message = "Weight cannot be less than 1")
    private double weight;
    private Duration duration;
}
