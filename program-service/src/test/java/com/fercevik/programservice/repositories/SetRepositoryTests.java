package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.repositories.SetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SetRepositoryTests {

    @Autowired
    private SetRepository repository;

    @Test
    public void testSaveAndRetrieve() {
        Set set = Set.builder().setNumber(1).reps(12).weight(32.5).duration(Duration.ofSeconds(60)).build();
        repository.save(set);

        Set savedSet = repository.findById(set.getSetId()).orElse(null);
        assertNotNull(savedSet);
        assertEquals(set, savedSet);

    }
}
