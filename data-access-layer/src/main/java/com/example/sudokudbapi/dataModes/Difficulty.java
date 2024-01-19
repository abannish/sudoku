package com.example.sudokudbapi.dataModes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import static com.example.sudokudbapi.staticMethods.JsonHandling.jsonToObject;
import static com.example.sudokudbapi.staticMethods.JsonHandling.objectToJson;

import jakarta.persistence.Column;
/*
 */

@Entity
@Table(name = "Difficulties")
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer", name = "difficulty_id", insertable = false)
    private int difficultyId;

    @Column(columnDefinition = "varchar(255)", name = "difficulty_name")
    private String difficultyName;
    
    public Difficulty() {}

    public Difficulty( String difficultyName) {
        this.difficultyName = difficultyName;
    }

    public int getDifficultyId() {
        return difficultyId;
    }
    public String getDifficultyName() {
        return difficultyName;
    }

    @Override
    public int hashCode() {
        return getDifficultyId() ^ getDifficultyName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(o.getClass() == this.getClass()) {
            Difficulty d = (Difficulty) o;
            return d.getDifficultyId() == getDifficultyId();
        }

        return false;
    }

    @Override
    public String toString() {
        return objectToJson(this);
    }

    public static Difficulty jsonDifficultyToObj(String json) {
        return jsonToObject(json,new Difficulty());
    }
}
