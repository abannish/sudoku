package com.example.sudokudbapi.dataModes;

import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import com.example.sudokudbapi.compositeKeys.FriendsPriKey;

@Entity
@Table(name = "Friends")
@IdClass(FriendsPriKey.class)
public class Friend {

    @Id
    private int userId;

    @Id
    private int friendId;

    public Friend() {}
    
    public Friend(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
    
    public int getUserId() {
        return userId;
    }

    public int getFriendId() {
        return friendId;
    }

    @Override
    public int hashCode() {
        return userId ^ friendId;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(o.getClass() == getClass()) {
            Friend f = (Friend) o;
            return f.friendId == friendId && f.userId == userId;
        }
        return false;
    }

    @Override
    public String toString() {
        return "{ userId:" + userId + ", friendId:" + friendId +"}";
    }
}
