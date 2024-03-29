package com.example.sudokudbapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Optional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.example.sudokudbapi.dataModes.User;
import com.example.sudokudbapi.repositories.UserRepo;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepo userRepository;

    @GetMapping
    public List<User> findAllUsers() {

        logger.debug("GET/api/user/:accessed:success");

        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") @NotNull long id) {

        logger.debug("GET/api/user/{}:accessed",id);

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            logger.debug("GET/api/user/{}:success:{}",id, user.get().toString());

            return ResponseEntity.ok().body(user.get());
        }

        logger.debug("GET/api/user/{}:not found",id);

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> saveUser(@Validated @RequestBody @NotNull User user) {

        try {

            ResponseEntity<URI> respose = ResponseEntity.created(new URI("/api/user/id="+user.getUserId())).build();

            userRepository.save(user);

            logger.debug("POST/api/user/:saved:{}",user.getUserId());
            
            return respose;
        }
        catch(URISyntaxException e) {
            logger.error("POST/api/user", e);
        }
        finally{
            logger.error("POST/api/user/{}",user.getUserId());
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") @NotNull long id) {

        logger.debug("DELTE/api/user/{}:accessed",id);

        Optional<User> maybeUser = userRepository.findById(id);

        if (maybeUser.isPresent()) {
            User u = maybeUser.get();

            userRepository.delete(u);

            logger.debug("DELTE/api/user/:deleted:{}", u.toString());

            return ResponseEntity.ok().body(u);
        }

        logger.debug("DELTE/api/user/:not found");
        
        return ResponseEntity.notFound().build();
    }
}
