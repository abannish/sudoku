package com.example.sudokudbapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sudokudbapi.dataModes.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);
    
}
