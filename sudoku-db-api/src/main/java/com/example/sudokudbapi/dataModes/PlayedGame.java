package com.example.sudokudbapi.dataModes;

import java.sql.Time;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

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
    @Column(columnDefinition = "integer", name = "game_id", nullable = false)
    private long gameId;

    @Column(columnDefinition = "integer", name = "move_set", nullable = false)
    private Set<Set<Integer>> moveSet;

    @Column(columnDefinition = "integer", name = "board_set", nullable = false)
    private Set<Set<Integer>> boardSet;

    @Column(columnDefinition = "time", name = "time_taken", nullable = false)
    private Time timeTaken;

    @ManyToOne(cascade = CascadeType.)
    @Column(columnDefinition = "integer", name = "difficulty_id", nullable = false)
    private Difficulty difficultyId;

    @Column(columnDefinition = "boolean", name = "finished", nullable = false)
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
