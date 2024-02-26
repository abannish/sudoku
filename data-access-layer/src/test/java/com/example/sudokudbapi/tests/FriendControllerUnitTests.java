package com.example.sudokudbapi.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.sudokudbapi.dataModes.Friend;
import com.example.sudokudbapi.dataModes.User;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerUnitTests {
    
    @Autowired
    private MockMvc mvc;

    private final String FRIEND_REQ_PATH = "/api/friends";
    private final String USER_REQ_PATH = "/api/user";

    private User testUser1;
    private User testUser2;

    private Friend testFriend;

    private String test1UserUri;
    private String test2UserUri;
    private String testFriendUri;

    private static <G> String postHelper(MockMvc mvc, G obj, String path) throws Exception {
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
    public void initializerTest() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void addFriendWithSuccess() throws Exception {
        testUser1 = new User("test-friend-1", "password", "username");
        testUser2 = new User("test-friend-2", "password", "username");

        test1UserUri = postHelper(mvc, testUser1, USER_REQ_PATH);
        test2UserUri = postHelper(mvc, testUser2, USER_REQ_PATH);

        testUser1 = User.jsonUserToObj(getHelper(mvc,test1UserUri));
        testUser2 = User.jsonUserToObj(getHelper(mvc,test2UserUri));

        testFriend = new Friend(testUser1.getUserId(),testUser2.getUserId());

        testFriendUri = postHelper(mvc, testFriend, FRIEND_REQ_PATH);

        testFriend = Friend.jsonFriendToObj(getHelper(mvc, testFriendUri));

        assertThat(testFriend).isNotNull();

        assert((FRIEND_REQ_PATH+"/id="+testFriend.getUserId()+"&friendId="+testFriend.getFriendId()).equals(testFriendUri));
    }

    @Test
    public void addFriendWithFailure() throws Exception {
        mvc.perform(post(FRIEND_REQ_PATH).contentType(MediaType.APPLICATION_JSON).content((new Friend()).toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getFriendWithSuccess() throws Exception {
        mvc.perform(get(testFriendUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(testFriend.toString()));
    }

    @Test
    public void getFriendWithFailure() throws Exception {
        mvc.perform(get(FRIEND_REQ_PATH+"/id=-1&friendId=-1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFriendWithSuccess() throws Exception {
        String json = mvc.perform(delete(testFriendUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();
        
        Friend deleted = Friend.jsonFriendToObj(json);

        assert(deleted.equals(testFriend));
    }

    @Test
    public void deleteFriendWithFailure() throws Exception {
        mvc.perform(delete(testFriendUri))
        .andExpect(status().isBadRequest());
    }

}
