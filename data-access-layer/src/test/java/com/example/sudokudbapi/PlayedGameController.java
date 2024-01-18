package com.example.sudokudbapi;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayedGameController {

    @Autowired
    private MockMvc mvc;

    private final String REQ_PATH = "/api/playedGame";
    private PlayedGame test_game;
    private String URI;

    @Test
    public void contextLoadsTest() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void addPlayedGameWithSuccess() throws Exception {
        Set<Set<Integer>> moveSet = new HashSet<>();
        Set<Set<Integer>> boardSet = new HashSet<>();
        Time t = new Time(0L);
        Difficulty d = new Difficulty(1,"TEST1");
        User player = new User();

        // move set, board set, time to time taken, difficulty of the board, is the board finished.
        test_game = new PlayedGame(moveSet,boardSet,t,d,false,player);
        MvcResult res = mvc.perform(post(REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(toJsonString(test_game)))
            .andExpect(status().isOk())
            .andReturn();
        
        URI = res.getAsyncResult().toString();
    }

    @Test
    public void addPlayedGameWithFailure() throws Exception {
        mvc.perform(post(REQ_PATH).contentType(MediaType.APPLICATION_JSON).content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getGameByIdWithSuccess() throws Exception {
        MvcResult res = mvc.perform(get(URI))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        PlayedGame g = (PlayedGame) res.getAsyncResult();

        assertThat(g.getBoardSet() == test_game.getBoardSet() &&
                   g.getMoveSet() == test_game.getMoveSet() &&
                   g.getDifficulty() == test_game.getDifficulty() &&
                   g.getFinished() == test_game.getFinished());

        test_game = g;
    }

    @Test
    public void getGameByIdWithFailure() throws Exception {
        mvc.perform(get(REQ_PATH+"/id=-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteGameWithSuccess() throws Exception {
        mvc.perform(delete(REQ_PATH+"/id="+test_game.getGameId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(test_game.toString()));
    }

    @Test
    public void deleteGameWithFailure() throws Exception {
        mvc.perform(delete(REQ_PATH+"/id="+test_game.getGameId()))
            .andExpect(status().isNotFound());
    }

    protected String toJsonString(final Object o) throws Exception {
        return (new ObjectMapper()).writeValueAsString(o);
    }
}
