package com.example.sudokudbapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sudokudbapi.dataModes.PlayedGame;

@Repository
public interface PlayedGameRepo extends CrudRepository<PlayedGame, Long>{}
