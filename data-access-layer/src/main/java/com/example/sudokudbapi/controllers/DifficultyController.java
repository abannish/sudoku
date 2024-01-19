package com.example.sudokudbapi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.example.sudokudbapi.dataModes.Difficulty;
import com.example.sudokudbapi.repositories.DifficultyRepo;

@RestController
@RequestMapping("/api/difficulty")
public class DifficultyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DifficultyRepo difficultyRepo;

    @GetMapping
    public List<Difficulty> findAllDifficulties() {

        logger.debug("GET/api/difficulty/:accessed");

        return (List<Difficulty>) difficultyRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Difficulty> findDifficulty(@PathVariable(name = "id") int id) {

        logger.debug("GET/api/difficulty/{}:accessed", id);

        Optional<Difficulty> difficulty = difficultyRepo.findById(id);

        if (difficulty.isPresent()) {

            logger.debug("GET/api/difficulty/{}:success:{}", id, difficulty.get().toString());

            return ResponseEntity.ok().body(difficulty.get());
        }

        logger.debug("GET/api/difficulty/{}:not found",id);

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> saveDifficulty(@Validated @RequestBody Difficulty difficulty) {
        
        String path = "/api/difficulty/id=";

        try {
            difficulty = difficultyRepo.save(difficulty);
        }
        catch(IllegalArgumentException e) {

        }

        logger.debug("POST/api/difficulty/:saved:{}", difficulty.getDifficultyId());

        return ResponseEntity.ok().body(URI.create(path + difficulty.getDifficultyId()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Difficulty> deleteDifficulty(@PathVariable(name = "id") int id) {
        return null;
    }
     
}
