package com.fercevik.programservice.utils;

import com.fercevik.programservice.dao.Exercise;
import com.fercevik.programservice.dao.Program;
import com.fercevik.programservice.dao.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RepoUtils {
    public static Program createProgram() {
        UUID ownerId = UUID.randomUUID();
        List<String> tags = new ArrayList<>();
        tags.add("Bodybuilding");
        tags.add("Powerlifting");
        return Program.builder().tags(tags).active(true).ownerId(ownerId).name("First Program").build();
    }

    public static Workout createWorkout() {
        return Workout.builder().description("Leg day! :)").name("Monday").date(new Date()).build();
    }

    public static Exercise createExercise() {
        return Exercise.builder().description("Biceps-focused and in the shortened position").target("Biceps")
                .name("Preacher Curls").build();
    }
}
