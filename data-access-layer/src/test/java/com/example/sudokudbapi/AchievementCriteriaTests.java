package com.example.sudokudbapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sudokudbapi.dataModes.AchievementCriteria;

@SpringBootTest
@AutoConfigureMockMvc
public class AchievementCriteriaTests {
    
    @Autowired
    private MockMvc mvc;

    private AchievementCriteria testCriteria;

    private String testCriteriaUri;


    @Test
    public void initializerTest() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void addAchievementCriteriaSuccess() throws Exception {
        // TODO
    }

    @Test
    public void getAchievementCriteriaSuccess() throws Exception {
        // TODO
    }

    @Test
    public void getAchievementCriteriaFailure() throws Exception {
        // TODO
    }

    @Test
    public void updateAchievementCriteriaSuccess() throws Exception {
        // TODO
    }

    @Test
    public void updateAchievementCriteriaFailure() throws Exception {
        // TODO
    }

    @Test
    public void deleteAchievementCriteriaSuccess() throws Exception {
        // TODO
    }

    @Test
    public void deleteAchievementCriteriaFailure() throws Exception {
        // TODO
    }
}
