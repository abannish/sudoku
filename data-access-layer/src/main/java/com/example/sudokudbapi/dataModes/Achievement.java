package com.example.sudokudbapi.dataModes;

import static com.example.sudokudbapi.staticMethods.JsonHandling.jsonToObject;
import static com.example.sudokudbapi.staticMethods.JsonHandling.objectToJson;

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

    protected Achievement() {}

    public Achievement(final AchievementCriteria achievementCriteria,final int criteriaValue) {
        this.achievementCriteria = achievementCriteria;
        this.criteriaValue = criteriaValue;
    }

    public int getAchievementId() {
        return achievementId;
    }
    public byte[] getAchievementImage() {
        return achievementImage;
    }
    public AchievementCriteria getCriteria() {
        return achievementCriteria;
    }
    public int getCriteriaValue() {
        return criteriaValue;
    }

    @Override
    public int hashCode() {
        return getAchievementId() ^ getCriteriaValue() ^ getCriteria().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(o.getClass() == this.getClass()) {
            Achievement a = (Achievement) o;
            return getAchievementId() == a.getAchievementId();
        }
        return false;
    }

    @Override
    public String toString() {
        return objectToJson(this);
    }

    public static Achievement jsonAchievementToObj(String json) {
        return jsonToObject(json,new Achievement());
    }
}
