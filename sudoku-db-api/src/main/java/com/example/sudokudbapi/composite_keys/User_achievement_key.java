package com.example.sudokudbapi.composite_keys;

import java.io.Serializable;

public final class User_achievement_key implements Serializable {

    private long user_id;

    private long achievement_id;

    public User_achievement_key() {}

    public User_achievement_key(long user_id, long achievement_id) {
        set_achievement_id(user_id);
        set_user_id(achievement_id);
    }

    public long get_user_id() {
        return user_id;
    }

    public long get_achievement_id() {
        return achievement_id;
    }

    public void set_user_id(long id) {
        user_id = id;
    }

    public void set_achievement_id(long id) {
        achievement_id = id;
    }

    @Override
    public int hashCode() {
        return (int) (user_id ^ achievement_id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        else if (o == this)
            return true;
        else if (o.getClass() == this.getClass()) {
            User_achievement_key uak = (User_achievement_key) o;
            return get_user_id() == uak.get_user_id() && get_achievement_id() == uak.get_achievement_id();
        }
        return false;
    }

    @Override
    public String toString() {
        return "{ user_id: " + get_user_id() + ", achievement_id: " + get_achievement_id() + "}";
    }
}
