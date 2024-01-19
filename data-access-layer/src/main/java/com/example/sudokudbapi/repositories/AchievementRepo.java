package com.example.sudokudbapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository; 

import com.example.sudokudbapi.dataModes.Achievement;

@Repository
public interface AchievementRepo extends CrudRepository<Achievement, Integer> {}
