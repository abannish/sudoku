package com.example.sudokudbapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository; 

import com.example.sudokudbapi.dataModes.Difficulty;

@Repository
public interface DifficultyRepo extends CrudRepository<Difficulty, Long> {}
