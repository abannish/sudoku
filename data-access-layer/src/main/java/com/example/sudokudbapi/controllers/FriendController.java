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

import com.example.sudokudbapi.compositeKeys.FriendsPriKey;
import com.example.sudokudbapi.dataModes.Friend;
import com.example.sudokudbapi.repositories.FriendRepo;

import jakarta.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping("/api/friends")
public class FriendController {
    private Logger logger = LoggerFactory.getLogger(FriendController.class);

    @Autowired
    private FriendRepo friendRepo;

    @GetMapping("/{userId}")
    public ResponseEntity<Set<Friend>> getFriends(@PathVariable(name = "userId") @NotNull long userId) {
        Iterable<Friend> friends = friendRepo.findAll();
        
        Set<Friend> fFriends = new HashSet<>();
        
        for(Friend f : friends) 
            if(f.getUserId() == userId)
                fFriends.add(f);

        return ResponseEntity.ok().body(fFriends);
    }

    @PostMapping
    public List<Friend> addFriends(@Validated @RequestBody @NotNull Set<Friend> newFriends) {
        return (List<Friend>) friendRepo.saveAll(newFriends);
    }

    @PostMapping
    public ResponseEntity<URI> addFriend(@Validated @RequestBody @NotNull Friend newFriend) {

        newFriend = friendRepo.save(newFriend);

        Friend inverseNewFriend = new Friend(newFriend.getFriendId(), newFriend.getUserId());

        friendRepo.save(inverseNewFriend);

        try {
            
            URI res = new URI("/api/friends/userId="+newFriend.getUserId()+"&friendId="+newFriend.getFriendId());

            return ResponseEntity.created(res).build();
        }
        
        catch (URISyntaxException e) {
            logger.error("PUT/api/friends/:", e);
        }
        
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{userId}{friendId}")
    public ResponseEntity<Friend> deleteFriend(@PathVariable(name = "userId") @NotNull int userId, @PathVariable(name = "friendId") @NotNull int friendId) {
        Optional<Friend> maybeFriend = friendRepo.findById(new FriendsPriKey(userId,friendId));
        
        if(maybeFriend.isPresent()) {

            Friend f = maybeFriend.get();
            friendRepo.delete(f);

            Friend invFriend = new Friend(friendId,userId);
            friendRepo.delete(invFriend);

            return ResponseEntity.ok().body(f);
        }

        return ResponseEntity.notFound().build(); 
    }
}
