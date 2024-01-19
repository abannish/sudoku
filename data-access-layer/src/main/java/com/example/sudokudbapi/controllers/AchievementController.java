package com.example.sudokudbapi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

import com.example.sudokudbapi.repositories.AchievementRepo;

import com.example.sudokudbapi.dataModes.Achievement;

@RestController
@RequestMapping("/api/achievement")
public class AchievementController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AchievementRepo achievementRepo;

    @GetMapping
    public List<Achievement> findAllAchievements() {

        logger.debug("GET/api/achievement/:accessed");
        
        return (List<Achievement>) achievementRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achievement> findAchievement(@PathVariable(name = "id") int id) {
        logger.debug("GET/api/achievement/{}:accesed", id);

        Optional<Achievement> achievement = achievementRepo.findById(id);

        if (achievement.isPresent()) {

            logger.debug("GET/api/achievement/{}:success:{}", id, achievement.get().toString());

            return ResponseEntity.ok().body(achievement.get());
        }

        logger.debug("GET/api/achievement/{}:not found", id);

        return ResponseEntity.notFound().build();
    }
}
