package com.example.sudokudbapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository; 

import com.example.sudokudbapi.dataModes.AchievementCriteria;

@Repository
public interface AchievementCriteriaRepo extends CrudRepository<AchievementCriteria, Integer> {}
