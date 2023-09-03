package com.fercevik.programservice.repositories;

import com.fercevik.programservice.dao.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {
}
