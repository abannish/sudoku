package com.example.sudokudbapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.sudokudbapi.dataModes.PlayedGame;

@Repository
public interface PlayedGameRepo extends CrudRepository<PlayedGame, Integer>, PagingAndSortingRepository<PlayedGame,Integer> {}
