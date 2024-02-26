package com.example.sudokudbapi.dataModes;

import java.sql.Time;
import java.util.Set;

import static com.example.sudokudbapi.staticMethods.JsonHandling.jsonToObject;
import static com.example.sudokudbapi.staticMethods.JsonHandling.objectToJson;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer", name = "user_id", nullable = false, insertable = false)
    private int userId;

    @NotNull
    @Column(columnDefinition = "varchar(45)", name = "username", nullable = false)
    @Pattern(regexp = "[A-Za-z1-9]{9-45}", message = "{invalid username}")
    private String username;

    @NotNull
    @Column(columnDefinition = "varchar(255)", name = "password", nullable = false)
    @Pattern(regexp = ".{9,255}", message = "{invalid password}")
    private String password;

    @NotNull
    @Column(columnDefinition = "varchar(255)", name = "email", nullable = false)
    @Pattern(regexp = ".+@.+\\..+", message = "{invalid email}")
    private String email;

    @Column(columnDefinition = "integer default '0'", name = "pvp_rank")
    private int pvpRank;
    @Column(columnDefinition = "integer default '0'", name = "pve_rank")
    private int pveRank;
    @Column(columnDefinition = "integer default '0'", name = "pvp_wins")
    private int pvpWins;
    @Column(columnDefinition = "integer default '0'", name = "pve_wins")
    private int pveWins;
    @Column(columnDefinition = "integer default '0'", name = "total_pvp_games")
    private int totalPvpGames;
    @Column(columnDefinition = "integer default '0'", name = "total_pve_games")
    private int totalPveGames;
    @Column(columnDefinition = "time default '00:00:00'", name = "time_played")
    private Time timePlayed;

    @Column(columnDefinition = "integer default '0'", name = "display_mode")
    private int displayMode;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userId")
    private Set<Friend> friends;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameId")
    private Set<PlayedGame> playedGames;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "achievementId")
    private Set<Achievement> achievements;

    protected User() { }

    public User(String username, String pass, String email) {
        this.username = username;
        this.password = pass;
        this.email = email;
    }
    // ================== getters ==================

    public int getUserId() {
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

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public Set<PlayedGame> getPlayedGames() {
        return playedGames;
    }

    public Set<Friend> getFriends() {
        return friends;
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
        return objectToJson(this);
    }

    public static User jsonUserToObj(String json) {
        return jsonToObject(json,new User());
    }
}