package com.example.sudokudbapi.dataModes;

import java.sql.Time;
import java.util.Set;

import static com.example.sudokudbapi.staticMethods.JsonHandling.jsonToObject;
import static com.example.sudokudbapi.staticMethods.JsonHandling.objectToJson;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * Description: Wrapper class for database data to represent a "played game" of sudoku.
 *              Only getter functions are available.
 * Table columns:
 *  gameId int
 *  userId int PK 
 *  moveSet blob 
 *  boardSet blob 
 *  timeTaken time 
 *  difficultyId int 
 *  finished boolean
 */

@Entity
@Table(name = "Played_game")
public class PlayedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer", nullable = false, insertable = false)
    private int gameId;

    @Column(columnDefinition = "blob", name = "move_set", nullable = false)
    private Set<Set<Integer>> moveSet;
    
    @Column(columnDefinition = "blob", name = "board_set", nullable = false)
    private Set<Set<Integer>> boardSet;
    
    @Column(columnDefinition = "time", name = "time_taken", nullable = false)
    private Time timeTaken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty", referencedColumnName = "difficulty_id", nullable = false)
    private Difficulty difficulty;

    @Column(columnDefinition = "boolean default false", name = "finished", nullable = false)
    private Boolean finished;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User player;

    public PlayedGame() {}

    public PlayedGame(Set<Set<Integer>> moveSet, Set<Set<Integer>> boardSet,Time time, Difficulty difficulty,Boolean finished, User player) {
        this.moveSet = moveSet;
        this.boardSet = boardSet;
        this.timeTaken = time;
        this.difficulty = difficulty;
        this.finished = finished;
        this.player = player;
    }

    public long getGameId() {
        return gameId;
    }

    public Set<Set<Integer>> getMoveSet() {
        return moveSet;
    }

    public Set<Set<Integer>> getBoardSet() {
        return boardSet;
    }

    public Time getTimeTaken() {
        return timeTaken;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean getFinished() {
        return finished;
    }
    
    @Override
    public int hashCode() {
        return ((int) gameId ^ moveSet.hashCode() ^ boardSet.hashCode() ^ timeTaken.hashCode() ^ difficulty.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(o.getClass() == this.getClass()) {
            PlayedGame pg = (PlayedGame) o;
            return pg.getGameId() == getGameId();
        }

        return false;
    }

    @Override
    public String toString() {
        return objectToJson(this);
    }

    public static PlayedGame jsonPlayedGameToObj(String json) {
        return jsonToObject(json,new PlayedGame());
    }
}
