package com.example.sudokudbapi.tests;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    private static final String USER_REQ_PATH = "/api/user";

    private String userUri;

    public static <G> String postHelper(MockMvc mvc,String path, G obj) throws Exception {
        MvcResult res = mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(obj.toString()))
        .andExpect(status().isCreated())
        .andReturn();

        return res.getResponse().getContentAsString();
    }

    public static String getHelper(MockMvc mvc, String uri) throws Exception {
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
    public void addUserWithSuccess() throws Exception {
        
        User testUser = new User("test-username","test-password","test-email");

        userUri = postHelper(mvc, USER_REQ_PATH, testUser);
        
        User tmpUser = User.jsonUserToObj(getHelper(mvc, userUri));

        assertThat(tmpUser).isNotNull();
        assert(tmpUser.getUsername() == testUser.getUsername());
        assert(tmpUser.getPassword() == testUser.getPassword());
        assert(tmpUser.getEmail() == testUser.getEmail());
    }

    @Test
    public void addUserWithUsernameFailure() throws Exception {
        User testUser = new User(null,"test-password","test-email");
        mvc.perform(post(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testUser.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserWithPasswordFailure() throws Exception {
        User testUser = new User("test-username",null,"test-email");
        mvc.perform(post(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testUser.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserWithEmailFailure() throws Exception {
        User testUser = new User("test-username","test-password",null);
        mvc.perform(post(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testUser.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserByIdWithSuccess() throws Exception {
        String json = getHelper(mvc, userUri);
    }

    @Test
    public void getUserByIdWithFailure() throws Exception {
        mvc.perform(get(USER_REQ_PATH+"/id=-1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserByUsernameAndPasswordWithSuccess() throws Exception {
        mvc.perform(get(USER_REQ_PATH+"username="+testUser.getUserId()+"&password="+testUser.getPassword()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(testUser.toString()));
    }

    @Test
    public void updateUserSuccess() throws Exception {
        testUser = new User("update-user","test-password","test-email");
        MvcResult res = mvc.perform(put(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(testUser.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        
        User updated = User.jsonUserToObj(res.getResponse().getContentAsString());

        assertThat(updated).isNotNull();
        assert(updated.equals(testUser));
    }
    @Test
    public void updateUserFailure() throws Exception {
        mvc.perform(put(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserFailureOnUsername() throws Exception {
        User tmp = new User(null,"test-password","test-email");
        mvc.perform(put(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(tmp.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserFailureOnPassword() throws Exception {
        User tmp = new User("update-user",null,"test-email");
        mvc.perform(put(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(tmp.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserFailureOnEmail() throws Exception {
        User tmp = new User("update-user","test-password",null);
        mvc.perform(put(USER_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(tmp.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserByIdWithSuccess() throws Exception {
        mvc.perform(delete(userUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(testUser.toString()));
    }

    @Test
    public void deleteUserByIdWithFailure() throws Exception {
        mvc.perform(delete(USER_REQ_PATH+"id=-1"))
            .andExpect(status().isBadRequest());
    }
}
