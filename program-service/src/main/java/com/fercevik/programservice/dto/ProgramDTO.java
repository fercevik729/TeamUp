package com.fercevik.programservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProgramDTO {
    private Long programId;
    private String name;
    private boolean active = false;
    private List<String> tags;
    private List<WorkoutDTO> workouts = new ArrayList<>();
}
