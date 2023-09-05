package com.fercevik.programservice.utils;

import com.fercevik.programservice.dao.Program;

import java.util.ArrayList;
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
}
