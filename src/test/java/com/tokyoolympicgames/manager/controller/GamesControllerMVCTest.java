package com.tokyoolympicgames.manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GamesControllerMVCTest {

    private final String URL = "/games/";
    private final String CORRECT_GAME = this.getGameJsonFromResources("Correct_Game.json");
    private final String SAME_COUNTRY_GAME = this.getGameJsonFromResources("SameCountry_Game.json");
    private final String WRONG_TIME_GAME = this.getGameJsonFromResources("WrongTime_Game.json");
    private final String NULL_FIELD_GAME = this.getGameJsonFromResources("NullField_Game.json");
    private final String DURATION_BELOW_GAME = this.getGameJsonFromResources("DurationBelow30_Game.json");
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws JsonProcessingException {

        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();

    }

    @Test
    public void testPersistGameCorrectly() throws Exception {

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(CORRECT_GAME))
                .andExpect(status().isCreated());
        mvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void testPersistGameWithNullOrEmptyFields() throws Exception {

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(NULL_FIELD_GAME))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Invalid: [modality can not be null or empty]\"]"));
        mvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void testPersistGameWhereTheBeginTimeIsEqualsToEndTime() throws Exception {

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(WRONG_TIME_GAME))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Invalid: End Time should be after the Begin time\"]"));
        mvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void testPersistGameWhereTheDurationIsBelow30Minutes() throws Exception {

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(DURATION_BELOW_GAME))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Invalid: The duration of the match is below 30 minutes\"]"));
        mvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void testPersistGameWhereStageIsNotFinalNorSemiFinalWithSameCountry() throws Exception {

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(SAME_COUNTRY_GAME))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Invalid: Using the same country in a not allowed in QUARTERFINAL Stage\"]"));
        mvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGameWithFilter() throws Exception {

        mvc.perform(get(URL + "?modality=Basketball"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    private String getGameJsonFromResources(String jsonFile) {

        String result;

        ClassLoader classLoader = getClass().getClassLoader();

        InputStream vendorJsonAsStream = classLoader.getResourceAsStream(jsonFile);
        result = new BufferedReader(new InputStreamReader(vendorJsonAsStream)).lines()
                .collect(Collectors.joining("\n"));

        return result;
    }

}
