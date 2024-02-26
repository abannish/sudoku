package com.example.sudokudbapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.example.sudokudbapi.dataModes.PlayedGame;
import com.example.sudokudbapi.repositories.PlayedGameRepo;


@RestController
@RequestMapping("/api/playedGame")
public class PlayedGameController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PlayedGameRepo gameRepo;

    @GetMapping
    public ResponseEntity<List<PlayedGame>> getAllGames(Pageable pageable) {
        Page<PlayedGame> page = gameRepo.findAllByUserId(
                PageRequest.of(pageable.getPageNumber(), 
                               pageable.getPageSize(),
                               pageable.getSortOr(Sort.by(Sort.Direction.ASC,"difficulty"))), 0);
        
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayedGame> findGame(@PathVariable(value = "id") int id) {
        logger.debug("GET/api/playedGame/{}:accessed",id);

        Optional<PlayedGame> game = gameRepo.findById(id);

        if (game.isPresent()) {

            logger.debug("GET/api/playedGame/{}:success:{}",id,game.get().toString());

            return ResponseEntity.ok().body(game.get());
        }

        logger.debug("GET/api/playedGame/{}:not found",id);

        return ResponseEntity.notFound().build();
    }

    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<URI> saveGame(@Validated @RequestBody  PlayedGame playedGame) {

        playedGame = gameRepo.save(playedGame);

        try {

            ResponseEntity<URI> response = ResponseEntity.created(new URI("/api/playedGame/id="+playedGame.getGameId())).build();

            logger.debug("POST/api/playedGame/:saved:{}",playedGame.getGameId());
            
            return response;
        }
        catch(URISyntaxException e) {
            logger.error("POST/api/playedGame", e);
        }
        finally{
            logger.error("POST/api/user/{}",playedGame.getGameId());
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlayedGame> deleteGame(@PathVariable(value = "id") int id) {

        logger.debug("DELTE/api/playedGame/{}:accessed",id);

        Optional<PlayedGame> game = gameRepo.findById(id);

        if (game.isPresent()) {

            logger.debug("DELTE/api/playedGame/{}:deleted:{}",id,game.get());

            gameRepo.delete(game.get());

            return ResponseEntity.ok().body(game.get());
        }

        logger.debug("DELTE/api/playedGame/{}:not found",id);

        return ResponseEntity.notFound().build();
    }
}
