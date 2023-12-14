package com.example.sudokudbapi.dataModes;

import java.sql.Time;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @NotNull
    @Column(columnDefinition = "varchar(45)", name = "username")
    @Pattern(regexp = "[A-Za-z1-9]{9-45}", message = "{invalid username}")
    private String username;

    @NotNull
    @Column(columnDefinition = "varchar(255)", name = "password")
    @Pattern(regexp = ".{9,255}", message = "{invalid password}")
    private String password;

    @NotNull
    @Column(columnDefinition = "carchar(255)", name = "email")
    @Pattern(regexp = ".+@.+\\..+", message = "{invalid email}")
    private String email;

    @Column(columnDefinition = "integer default '0'", name = "pvpRank")
    private int pvpRank;
    @Column(columnDefinition = "integer default '0'", name = "pveRank")
    private int pveRank;
    @Column(columnDefinition = "integer default '0'", name = "pvp_wins")
    private int pvpWins;
    @Column(columnDefinition = "integer default '0'", name = "pve_wins")
    private int pveWins;
    @Column(columnDefinition = "integer default '0'", name = "total_pvp_games")
    private int totalPvpGames;
    @Column(columnDefinition = "integer default '0'", name = "total_pve_games")
    private int totalPveGames;
    @Column(columnDefinition = "time default '0'", name = "time_played")
    private Time timePlayed;
    @Column(columnDefinition = "Integer default '0'", name = "display_mode")
    private int displayMode;

    // one game to many players
    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<Played_game> playedGames;

    // ================== getters ==================

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getPvpRank() {
        return pvpRank;
    }

    public int getPveRank() {
        return pveRank;
    }

    public int getPvpWins() {
        return pvpWins;
    }

    public int getPveWins() {
        return pveWins;
    }

    public int getTotalPvpGames() {
        return totalPvpGames;
    }

    public int getTotalPveGames() {
        return totalPveGames;
    }

    public int getDisplayMode() {
        return displayMode;
    }

    public Time getTimePlayed() {
        return timePlayed;
    }

    // ================== setters ==================

    public Set<Played_game> getPlayedGames() {
        return playedGames;
    }

    public void setUserId(long id) {
        userId = id;
    }

    public void setUsername(String u) {
        username = u;
    }

    public void setPassword(String p) {
        password = p;
    }

    public void setEmail(String e) {
        email = e;
    }

    public void setPvpRank(int r) {
         pvpRank = r;
    }

    public void setPveRank(int r) {
        pveRank = r;
    }

    public void setPvpWins(int w) {
        pvpWins = w;
    }

    public void setPveWins(int w) {
        pveWins = w;
    }

    public void setTotalPvpGames(int tpg) {
        totalPvpGames = tpg;
    }

    public void setTotalPveGames(int tpg) {
        totalPveGames = tpg;
    }

    public void setTimePlayed(Time t) {
        timePlayed = t;
    }

    public void setDisplayMode(int dm) {
        displayMode = dm;
    }

    public void setPlayedGames(Set<Played_game> games) {
        playedGames = games;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        else if (o == this)
            return true;
        else if (o.getClass() == this.getClass()) {
            User u = (User) o;
            return getUserId() == u.getUserId();
        }

        return false;
    }

    @Override
    public int hashCode() {
        // userId XOR'ed with timePlayed hashcode
        return (int) getUserId() ^ getTimePlayed().hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "userId: " + getUserId() +
                ",username: " + getUsername() +
                ",password: " + getPassword() +
                ",email: " + getEmail() +
                ",pvpRank: " + getPvpWins() +
                ",pveRank: " + getPveRank() +
                ",pvpWins: " + getPvpWins() +
                ",pveWinds: " + getPveWins() +
                ",totalPvpWins: " + getTotalPvpGames() +
                ",totalPveWins: " + getTotalPveGames() +
                ",display_mode: " + getDisplayMode() +
                ",timePlayed: " + getTimePlayed().toString() +
                "}";
    }
}