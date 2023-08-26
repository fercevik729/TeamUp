package com.fercevik.programservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseDTO {
    private Long exerciseId;
    private String name;
    private String description;
    private String target;
    private List<SetDTO> sets;
}
