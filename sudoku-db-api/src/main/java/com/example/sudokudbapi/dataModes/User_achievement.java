package com.example.sudokudbapi.dataModes;

import com.example.sudokudbapi.composite_keys.User_achievement_key;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.IdClass;

@Entity
@IdClass(User_achievement_key.class)
public class User_achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long achievement_id;

    @Id
    private long user_id;

    public User_achievement() {}

    public User_achievement(long achievementId, long userId) {
        setAchievementId(achievementId);
        setUserId(userId);
    }

    public void setAchievementId(long id) {
        achievement_id = id;
    }

    public void setUserId(long id) {
        user_id = id;
    }

    public long getUserId() {
        return user_id;
    }

    public long getAchievmentId() {
        return achievement_id;
    }

    @Override
    public int hashCode() {
        return (int) (achievement_id ^ user_id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        else if (o == this)
            return true;
        else if (o.getClass() == this.getClass()) {
            User_achievement ua = (User_achievement) o;

            return ua.getAchievmentId() == getAchievmentId() && ua.getUserId() == getUserId();
        }

        return false;
    }

    @Override
    public String toString() {
        return "{ AchievementId: " + 
                    getAchievmentId() + 
                    ", userId: " + 
                    getUserId() + 
                    " }";
    }
}
