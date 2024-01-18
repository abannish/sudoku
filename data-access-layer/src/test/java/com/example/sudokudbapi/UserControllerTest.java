package com.example.sudokudbapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.sudokudbapi.dataModes.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String REQ_PATH = "/api/user";

    private static User testUser = new User("test-username","test-password","test-email");

    private String userUri;

    @Test
    public void contextLoadsTest() throws Exception {
        assertThat(mvc).isNotNull();
    }
    
    // test must be run after contextLoadsTest
    @Test
    public void addUserWithSuccess() throws Exception {
        MvcResult res = mvc.perform(post(REQ_PATH).content(testUser.toString()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
        
            userUri = res.getAsyncResult().toString();
    }

    @Test
    public void addUserWithFailure() throws Exception {
        mvc.perform(post(REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(""))
            .andExpect(status().isBadRequest());
    }

    // test must be run after addUserWithSuccess
    @Test
    public void getUserByIdWithSuccess() throws Exception {
        mvc.perform(get(userUri)) // get the test user from the db
            .andExpect(status().isOk()) // make sure there is a status code of 200
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // content type is correct
            .andExpect(content().json(testUser.toString())); // make sure the user from the db and the test user are the same
    }

    @Test
    public void getUserByIdWithFailure() throws Exception {
        mvc.perform(get(REQ_PATH+"/id=0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));
    }

    // test must be run after addUserWithSuccess
    @Test
    public void getUserByUsernameAndPasswordWithSuccess() throws Exception {
        mvc.perform(get(REQ_PATH+"/username="+testUser.getUsername()+"&password="+testUser.getPassword()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(testUser.toString()));
    }

    // test must be run after addUserWithSuccess
    @Test
    public void deleteUserByIdWithSuccess() throws Exception {
        mvc.perform(delete(REQ_PATH+"/id="+testUser.getUserId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(testUser.toString()));
    }

    // test must be run after deleteUserByIdWithSuccess
    @Test
    public void deleteUserByIdWithFailure() throws Exception {
        mvc.perform(delete(REQ_PATH+"/id="+testUser.getUserId()))
            .andExpect(status().isNotFound());
    }



}
