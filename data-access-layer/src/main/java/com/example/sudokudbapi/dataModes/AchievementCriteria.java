package com.example.sudokudbapi.dataModes;

import static com.example.sudokudbapi.staticMethods.JsonHandling.jsonToObject;
import static com.example.sudokudbapi.staticMethods.JsonHandling.objectToJson;

import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "ACHIEVEMENT_CRITERIA")
public class AchievementCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer", name = "criteria_id", nullable = false, insertable = false)
    private int criteriaId;

    @Column(columnDefinition = "varchar(255)", name = "criteria_description")
    private String criteriaDescription;

    protected AchievementCriteria() {}

    public AchievementCriteria(String description) {
        criteriaDescription = description;
    }

    public int getCriteriaId() {
        return criteriaId;
    }

    public String getCriteriaDescription() {
        return criteriaDescription;
    }

    @Override
    public int hashCode() {
        return getCriteriaId() ^ getCriteriaDescription().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(o.getClass() == this.getClass()) {
            AchievementCriteria ac = (AchievementCriteria) o;

            return getCriteriaId() == ac.getCriteriaId() && getCriteriaDescription().equals(ac.getCriteriaDescription());
        }

        return false;
    }

    @Override
    public String toString() {
        return objectToJson(this);
    }

    public static AchievementCriteria jsonAchievementCriteriaToObj(String json) {
        return jsonToObject(json,new AchievementCriteria());
    }
}
