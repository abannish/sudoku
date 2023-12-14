package com.example.sudokudbapi.dataModes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/*
 * Description: Wrapper class for a difficulty in Sudoku game with attributes
 *              and getters.
 */

@Entity
@Table(name = "Difficulties")
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long difficultyId;
    private String difficultyName;
    
    public long getDifficultyId() {
        return difficultyId;
    }
    public String getDifficultyName() {
        return difficultyName;
    }

}
