package com.example.sudokudbapi.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.sudokudbapi.dataModes.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    private static final String USER_REQ_PATH = "/api/user";

    private String userUri;

    private static <G> String postHelper(MockMvc mvc,String path, G obj) throws Exception {
        MvcResult res = mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(obj.toString()))
        .andExpect(status().isCreated())
        .andReturn();

        return res.getResponse().getContentAsString();
    }

    private static String getHelper(MockMvc mvc, String uri) throws Exception {
        MvcResult res = mvc.perform(get(uri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        
        return res.getResponse().getContentAsString();
    }

    @Test
    public void contextLoadsTest() throws Exception {
        assertThat(mvc).isNotNull();
    }
    
    @Test
    @DirtiesContext
    void addUserWithSuccess() throws Exception {
        User testUser = new User("testUsername2", "password2", "test@email.com");

        MvcResult res = mvc.perform(post(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testUser.toString()))
            .andExpect(status().isCreated())
            .andReturn();
        
        String uri = res.getResponse().getHeader("location");

        System.out.println(uri);

        res = mvc.perform(get(uri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        User savedUser = User.jsonUserToObj(res.getResponse().getContentAsString());

        assertThat(savedUser.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(savedUser.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    void addUserWithFailure() throws Exception {
        //TODO
    }

    @Test
    @DirtiesContext
    void getUserByIdWithSuccess() throws Exception {
        //TODO
    }

    @Test
    void getUserByIdWithFailure() throws Exception {
        //TODO
    }

    @Test
    void getUserByUsernameWithSuccess() throws Exception {
        //TODO
    }

    @Test
    void getUserByUsernameWithFailure() throws Exception {
        //TODO
    }

    @Test
    @DirtiesContext
    void updateUserWithSuccess() throws Exception {
        //TODO
    }

    @Test
    void updateUserWithFailure() throws Exception {
        //TODO
    }
    
    @Test
    void deleteUserWithSuccess() throws Exception {
        //TODO
    }

    @Test
    void deleteUserWithFailure() throws Exception {
        //TODO
    }
}
