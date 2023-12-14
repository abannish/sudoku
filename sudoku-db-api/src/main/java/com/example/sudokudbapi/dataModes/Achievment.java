package com.example.sudokudbapi.dataModes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * Description: Wrapper class for a achievement in sudoku. This class only contains attributes and getters.
 * 
 * Table columns:
 * idachievements int 
 * achievementImage longblob 
 * criteraType varchar(45) 
 * criteraValue int
 */

@Entity
@Table(name = "achievements")
public class Achievment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long achievement_id;
    private byte[] achievement_image;
    private String critera_type;
    private int critera_value;

    public long getAchievmentId() {
        return achievement_id;
    }
    public byte[] getAchievementImage() {
        return achievement_image;
    }
    public String getCritera() {
        return critera_type;
    }
    public int getCriteraValue() {
        return critera_value;
    }
}
