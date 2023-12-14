package com.example.sudokudbapi.dataModes;

import java.sql.Time;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * Description: Wrapper class for databse data to represent a "played game" of sudoku.
 *              Only getter functions are avavilable.
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
public class Played_game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long gameId;

    private Set<Set<Integer>> moveSet;
    private Set<Set<Integer>> boardSet;

    private Time timetaken;
    private long difficultyId;
    private boolean finished;

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
        return timetaken;
    }

    public long getDifficulty() {
        return difficultyId;
    }

    public boolean getFinished() {
        return finished;
    }
}
