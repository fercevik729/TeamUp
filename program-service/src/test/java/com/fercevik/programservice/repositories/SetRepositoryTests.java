package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Set;
import com.fercevik.programservice.repositories.SetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "spring.cloud.vault.enabled=false")
@ActiveProfiles("test")
public class SetRepositoryTests {

    @Autowired
    private SetRepository repository;

    @Test
    public void testSaveAndRetrieve() {
        Set set = Set.builder().setId(1L).reps(12).weight(32.5).duration(60).build();
        repository.save(set);

        Set savedSet = repository.findById(set.getSetId()).orElse(null);
        assertNotNull(savedSet);
        assertEquals(set, savedSet);

    }
}
