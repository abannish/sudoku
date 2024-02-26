package com.example.sudokudbapi.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.sudokudbapi.dataModes.Difficulty;
import com.example.sudokudbapi.dataModes.PlayedGame;
import com.example.sudokudbapi.dataModes.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

// Tests all end points of the playedGameController with both success and failure codes.
// Tests must be carried out in sequence, entire class must be tested at one time.
@SpringBootTest
@AutoConfigureMockMvc
public class PlayedGameControllerTests {

    @Autowired
    private MockMvc mvc;

    // end point urls
    private final String PLAYED_GAME_REQ_PATH = "/api/playedGame";
    private final String USER_REQ_PATH = "/api/user";
    private final String DIFFICULTY_REQ_PATH = "/api/difficulty";

    // test to test memory
    private PlayedGame testGame;
    private Difficulty testDifficulty = new Difficulty("test-difficulty");
    private User testPlayer = new User("test-user", "test-password", "test-email");
    private String testGameUri;
    private String testDifficultyUri;
    private String testUserUri;

    private static <G> String postHelper(MockMvc mvc,String path, G obj) throws Exception {
        MvcResult res = mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(obj.toString()))
            .andExpect(status().isCreated())
            .andReturn();

        return res.getResponse().getContentAsString();
    }

    private static String getHelper(MockMvc mvc,String uri) throws Exception {
        MvcResult res = mvc.perform(get(uri))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

        return res.getResponse().getContentAsString();

    }
    private static <G> String putHelper(MockMvc mvc, String uri, G obj) throws Exception {
        MvcResult res = mvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(obj.toString()))
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
    public void addPlayedGameWithSuccess() throws Exception {

        testUserUri = postHelper(mvc, USER_REQ_PATH, testPlayer);
        
        testPlayer = User.jsonUserToObj(getHelper(mvc, testUserUri)); // get in db obj
        
        testDifficultyUri = postHelper(mvc, DIFFICULTY_REQ_PATH, testDifficulty);
            
        testDifficulty = Difficulty.jsonDifficultyToObj(getHelper(mvc, testDifficultyUri));

        Set<Set<Integer>> moves = new HashSet<>();
        Set<Set<Integer>> board = new HashSet<>();

        testGame = new PlayedGame(moves, board, new Time(0), testDifficulty, false, testPlayer);
        
        testGameUri = postHelper(mvc, PLAYED_GAME_REQ_PATH, testGame); // retrieve uri

        testGame = PlayedGame.jsonPlayedGameToObj(getHelper(mvc, testGameUri));

    }

    @Test
    public void addPlayedGameWithFailureOnMoveSet() throws Exception {
        Set<Set<Integer>> boardSet = new HashSet<>();

        PlayedGame tmp = new PlayedGame(null,boardSet, new Time(0), testDifficulty, false, testPlayer);

        String badUri = postHelper(mvc, PLAYED_GAME_REQ_PATH,tmp); // might error

        PlayedGame.jsonPlayedGameToObj(getHelper(mvc, badUri)); // will error
    }

    @Test
    public void addPlayedGameWithFailureOnBoardSet() throws Exception {
        Set<Set<Integer>> moves = new HashSet<>();

        PlayedGame tmp = new PlayedGame(moves,null, new Time(0), testDifficulty, false, testPlayer);

        String badUri = postHelper(mvc, PLAYED_GAME_REQ_PATH,tmp); // might error

        PlayedGame.jsonPlayedGameToObj(getHelper(mvc, badUri)); // will error
    }

    @Test
    public void addPlayedGameWithFailureOnTime() throws Exception {
        Set<Set<Integer>> moves = new HashSet<>();
        Set<Set<Integer>> board = new HashSet<>();

        PlayedGame tmp = new PlayedGame(moves,board, null, testDifficulty, false, testPlayer);

        String badUri = postHelper(mvc, PLAYED_GAME_REQ_PATH,tmp); // might error

        PlayedGame.jsonPlayedGameToObj(getHelper(mvc, badUri)); // will error
    }

    @Test
    public void addPlayedGameWithFailureOnDifficulty() throws Exception {
        Set<Set<Integer>> moves = new HashSet<>();
        Set<Set<Integer>> board = new HashSet<>();

        PlayedGame tmp = new PlayedGame(moves,board, new Time(0), null, false, testPlayer);

        String badUri = postHelper(mvc, PLAYED_GAME_REQ_PATH,tmp); // might error

        PlayedGame.jsonPlayedGameToObj(getHelper(mvc, badUri)); // will error
    }

    @Test
    public void addPlayedGameWithFailureOnFinished() throws Exception {
        Set<Set<Integer>> moves = new HashSet<>();
        Set<Set<Integer>> board = new HashSet<>();

        PlayedGame tmp = new PlayedGame(moves,board, new Time(0), testDifficulty, null, testPlayer);

        String badUri = postHelper(mvc, PLAYED_GAME_REQ_PATH,tmp); // might error

        PlayedGame.jsonPlayedGameToObj(getHelper(mvc, badUri)); // will error
    }

    @Test
    public void addPlayedGameWithFailureOnPlayer() throws Exception {
        Set<Set<Integer>> moves = new HashSet<>();
        Set<Set<Integer>> board = new HashSet<>();

        PlayedGame tmp = new PlayedGame(moves,board, new Time(0), testDifficulty, false, null);

        String badUri = postHelper(mvc, PLAYED_GAME_REQ_PATH,tmp); // might error

        PlayedGame.jsonPlayedGameToObj(getHelper(mvc, badUri)); // will error
    }

    @Test
    public void getGameByIdWithSuccess() throws Exception {
        String json = getHelper(mvc, testGameUri);
        PlayedGame tmp = PlayedGame.jsonPlayedGameToObj(json);
        assertThat(tmp).isNotNull();
        assertThat(tmp.equals(testGame));
    }

    @Test
    public void getGameByIdWithFailure() throws Exception {
        String json = getHelper(mvc, PLAYED_GAME_REQ_PATH+"/id=-1");
        assertThat(json).isNull();
    }

    @Test
    public void updateGameWithSuccess() throws Exception {

        PlayedGame copy = new PlayedGame(testGame.getMoveSet(), 
                                        testGame.getBoardSet(), 
                                        testGame.getTimeTaken(),
                                        testDifficulty, 
                                        true, 
                                        testPlayer);

        PlayedGame updated = PlayedGame.jsonPlayedGameToObj(putHelper(mvc, PLAYED_GAME_REQ_PATH, copy));

        assertThat(updated.equals(copy) && !testGame.equals(copy));

        testGame = updated;
    }

    @Test
    public void deleteGameWithSuccess() throws Exception {
        MvcResult res = mvc.perform(delete(testGameUri))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = res.getResponse().getContentAsString();

        PlayedGame deleted = PlayedGame.jsonPlayedGameToObj(json);

        assertThat(deleted.equals(testGame));
    }

    @Test
    public void deleteGameWithFailure() throws Exception {
        mvc.perform(delete(testGameUri))
            .andExpect(status().isBadRequest());
    }
}
