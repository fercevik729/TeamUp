package com.fercevik.programservice.repositories;

import com.fercevik.programservice.repositories.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkoutRepositoryTests {

    @Autowired
    private WorkoutRepository repository;

    @Test
    public void testSaveAndRetrieve() {

    }

}
