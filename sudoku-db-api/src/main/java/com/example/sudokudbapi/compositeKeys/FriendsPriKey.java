package com.example.sudokudbapi.compositeKeys;

import java.io.Serializable;

public class FriendsPriKey implements Serializable {
    private int userId;

    private int friendId;

    public FriendsPriKey() {
    }

    public FriendsPriKey(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    @Override
    public int hashCode() {
        return userId ^ friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        else if (o == this)
            return true;
        else if (o.getClass() == getClass()) {
            FriendsPriKey fpk = (FriendsPriKey) o;
            return fpk.friendId == friendId && fpk.userId == userId;
        }
        return false;
    }

    @Override
    public String toString() {
        return "{ userId: "+ userId + ", friendId: " + friendId + "}";
    }
}
