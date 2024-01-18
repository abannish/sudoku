package com.example.sudokudbapi.dataModes;

import jakarta.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Achievement_criteria")
public class AchievementCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer", name = "criteria_id", nullable = false, insertable = false)
    private int criteriaId;

    @Column(columnDefinition = "varchar(255)", name = "criteria_description")
    private String criteraDescription;

    public int getCriteriaId() {
        return criteriaId;
    }

    public String getCriteriaDescription() {
        return criteraDescription;
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
        try {
            return new ObjectMapper().writeValueAsString(this);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
