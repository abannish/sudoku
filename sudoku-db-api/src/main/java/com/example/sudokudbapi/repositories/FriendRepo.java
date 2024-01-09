package com.example.sudokudbapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sudokudbapi.compositeKeys.FriendsPriKey;
import com.example.sudokudbapi.dataModes.Friend;

@Repository
public interface FriendRepo extends CrudRepository<Friend, FriendsPriKey> {}
