package com.fercevik.programservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorkoutDTO {
    private Long workoutId;
    private String name;
    private String description;
    private Date date;
    private List<ExerciseDTO> exercises;
}
