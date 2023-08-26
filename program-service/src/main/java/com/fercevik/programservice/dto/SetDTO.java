package com.fercevik.programservice.dto;

import lombok.Data;

import java.time.Duration;

@Data
public class SetDTO {
    private Long setId;
    private int setNumber;
    private int reps;
    private double weight;
    private Duration duration;
}
