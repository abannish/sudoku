package com.example.sudokudbapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.sudokudbapi.dataModes.Achievement;
import com.example.sudokudbapi.dataModes.AchievementCriteria;

// SpringBootTest annotation signals for the application context to be loaded.
// All tests must happen in sequence, later tests have dependencies on previous tests succeeding
@SpringBootTest
@AutoConfigureMockMvc
public class AchievementControllerTests {

    @Autowired
    private MockMvc mvc;

    // paths to controller end points
    private final String ACHIEVEMENT_REQ_PATH = "/api/achievement";
    private final String ACHIEVEMENT_CRITERIA_REQ_PATH = "/api/achievementCriteria";
    
    // test to test memory
    private AchievementCriteria testCriteria = new AchievementCriteria("TEST_CRITERIA");
    private Achievement testAchievement;
    private String testCriteriaUri;
    private String testAchievementUri;

    @Test
    public void contextLoadsTest() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void addAchievementWithSuccess() throws Exception {
        MvcResult res; // used to retrieve data.
        
        res = mvc.perform(post(ACHIEVEMENT_CRITERIA_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testCriteria.toString()))
            .andExpect(status().isCreated()).andReturn(); // returns URI for AchievementCriteria if status is ok
        
        testCriteriaUri = res.getResponse().getContentAsString(); // get the uri for test criteria

        res = mvc.perform((get(testCriteriaUri)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn(); // get the test criteria that was inserted into the database in order to get populate the primary key
    
        testCriteria = AchievementCriteria.jsonAchievementCriteriaToObj(res.getResponse().getContentAsString()); // get the return json achievement criteria

        assertThat(testCriteria != null); 

        testAchievement = new Achievement(testCriteria, -1); // create a new achievement with inserted criteria

        res =  mvc.perform(post(ACHIEVEMENT_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testAchievement.toString()))
            .andExpect(status().isCreated())
            .andReturn(); // controller returns URI to the inserted achievement

        testAchievementUri = res.getResponse().getContentAsString();

        assertThat(testAchievementUri != null); 
    }

    @Test
    public void addAchievementWithFailure() throws Exception {
        mvc.perform(post(ACHIEVEMENT_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(""))
            .andExpect(status().isBadRequest()); // bad request is returned when it is one
    }

    @Test
    public void getAchievementWithSuccess() throws Exception {
        MvcResult res = mvc.perform(get(testAchievementUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn(); // get the inputted achievement from addAchievementWithSuccess
        
        Achievement test2 = Achievement.jsonAchievementToObj(res.getResponse().getContentAsString()); // convert to obj

        assertThat(test2.getCriteria().equals(testAchievement.getCriteria()) 
                    && test2.getCriteriaValue() == testAchievement.getCriteriaValue()); // ensure achievements are equal

        testAchievement = test2;
    }

    @Test
    public void getAchievementWithFailure() throws Exception {
        mvc.perform(get(ACHIEVEMENT_REQ_PATH+"/id=-1"))
            .andExpect(status().isBadRequest()); // get operation returns bad request when it is one, id=-1 is a invalid id 
    }

    @Test
    public void deleteAchievementWithSuccess() throws Exception {
        mvc.perform(delete(testAchievementUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(testAchievement.toString())); // delete operation returns the resource
    }

    @Test
    public void deleteAchievementWithFailure() throws Exception {
        mvc.perform(delete(testAchievementUri))
            .andExpect(status().isBadRequest()); // ensure a bad request is returned when it is one
        
        mvc.perform(delete(testCriteriaUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}