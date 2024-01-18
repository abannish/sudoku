package com.example.sudokudbapi.dataModes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * Description: Wrapper class for a achievement in sudoku. This class only contains attributes and getters.
 * 
 */

@Entity
@Table(name = "Achievements")
public class Achievement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer", name = "achievement_id", insertable = false)
    private int achievementId;

    @Column(columnDefinition = "longblob default null", name = "achievement_image")
    private byte[] achievementImage;

    @ManyToOne
    @JoinColumn(name = "criteria_id", updatable = false)
    private AchievementCriteria achievementCriteria;

    @Column(columnDefinition = "integer", name = "criteria_value")
    private int criteriaValue;

    public int getAchievmentId() {
        return achievementId;
    }
    public byte[] getAchievementImage() {
        return achievementImage;
    }
    public AchievementCriteria getCriteria() {
        return achievementCriteria;
    }
    public int getcriteriaValue() {
        return criteriaValue;
    }

    @Override
    public int hashCode() {
        return getAchievmentId() ^ getcriteriaValue() ^ getCriteria().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(o.getClass() == this.getClass()) {
            Achievement a = (Achievement) o;
            return getAchievmentId() == a.getAchievmentId();
        }
        return false;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this); 
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
