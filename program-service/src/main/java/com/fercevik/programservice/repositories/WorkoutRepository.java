package com.fercevik.programservice.repositories;

import com.fercevik.programservice.models.Program;
import com.fercevik.programservice.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findWorkoutsByProgram(Program program);


}
