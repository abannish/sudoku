package com.example.sudokudbapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sudokudbapi.dataModes.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
