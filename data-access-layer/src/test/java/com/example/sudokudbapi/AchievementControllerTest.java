package com.example.sudokudbapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

//SpringBootTest annotation signals for the application context to be loaded.
@SpringBootTest
@AutoConfigureMockMvc // 
public class AchievementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // path to achievement controller
    private static final String REQ_PATH = "/api/achievement/"; 

    @Test
    public void contextLoadsTest() throws Exception {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void getAchievementWithSuccess() throws Exception {
        long achievementId = 4;
        String json = "{achievementImage:null,criteriaValue:1,achievmentId:4,criteria:{criteriaId:1,criteriaDescription:TEST}}";
        
        mockMvc.perform(get(REQ_PATH+achievementId)).andDo(print()).andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(json));
    }

    @Test
    public void getAchievementWithFailure() throws Exception {
        long nonExistentId = 0L;                                             
        mockMvc.perform(get(REQ_PATH + nonExistentId)).andDo(print()).andExpect(status().isNotFound());
    }
}
