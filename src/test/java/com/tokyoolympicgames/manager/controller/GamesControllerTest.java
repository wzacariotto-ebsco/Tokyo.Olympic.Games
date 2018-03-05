package com.tokyoolympicgames.manager.controller;

import com.tokyoolympicgames.manager.entity.Country;
import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;
import com.tokyoolympicgames.manager.enums.Stage;
import com.tokyoolympicgames.manager.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class GamesControllerTest {

    @Mock
    private GameService service;

    @InjectMocks
    private GameController controller;

    private Game game;

    private Game game2;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        game = new Game(LocalDateTime.now(), LocalDateTime.now()
                .plusHours(3l), new Modality("Soccer"), new Country("Brazil"), new Country("Germany"), new Localization("Tokyo"),
                Stage.FINAL);
        game2 = new Game(LocalDateTime.now()
                .plusDays(1), LocalDateTime.now()
                .plusDays(1)
                .plusHours(2), new Modality("Soccer"), new Country("Spain"), new Country("USA"), new Localization("Tokyo"),
                Stage.FINAL);
    }

    @Test
    public void testPersistGameCorrectly() throws Exception {

        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(new ResponseEntity<>(game, HttpStatus.CREATED), controller.persistGame(game));
    }

    @Test
    public void testPersistGameWithNullOrEmptyFields() throws Exception {

        game.setLocal(new Localization(""));
        game.setModality(null);
        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(
                Arrays.asList("Invalid: [modality can not be null or empty]"),
                controller.persistGame(game)
                        .getBody());
    }

    @Test
    public void testPersistGameWhereThereAre4GamesInThatLocal() throws Exception {

        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game, game, game, game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: There are 4 games in this local in the same day"),
                controller.persistGame(game)
                        .getBody());
    }

    @Test
    public void testPersistGameWhereStageIsNotFinalNorSemiFinalWithSameCountry() throws Exception {

        game.setStage(Stage.EIGHTFINAL);
        game.setFirstCountry(new Country("Brazil"));
        game.setSecondCountry(new Country("Brazil"));
        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game, game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: Using the same country in a not allowed in EIGHTFINAL Stage"),
                controller.persistGame(game)
                        .getBody());
    }

    @Test
    public void testPersistGameWhereTheBeginTimeIsHigherThanEndTime() throws Exception {

        game.setBeginTime(LocalDateTime.now()
                .plusHours(1));
        game.setEndTime(LocalDateTime.now());

        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: End Time should be after the Begin time"),
                controller.persistGame(game)
                        .getBody());
    }

    @Test
    public void testPersistGameWhereTheBeginTimeIsEqualsToEndTime() throws Exception {

        game.setBeginTime(LocalDateTime.now());
        game.setEndTime(LocalDateTime.now());

        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: The duration of the match is below 30 minutes",
                "Invalid: End Time should be after the Begin time"), controller.persistGame(game)
                .getBody());
    }

    @Test
    public void testPersistGameWhereTheDurationIsBelow30Minutes() throws Exception {

        game.setEndTime(LocalDateTime.now()
                .plusMinutes(10));
        game.setBeginTime(LocalDateTime.now());

        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: The duration of the match is below 30 minutes"),
                controller.persistGame(game)
                        .getBody());
    }

    @Test
    public void testPersistGameWhereThereAnotherOneSimilarHappeningInTheSameTime() throws Exception {

        Mockito.when(service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.findByLocal(new Localization("Tokyo")))
                .thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game))
                .thenReturn(game);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game)
                .getStatusCode());
        Assert.assertEquals(
                Arrays.asList("Invalid: There is a game of the this modality happening in the same time and Local"),
                controller.persistGame(game)
                        .getBody());
    }

    @Test
    public void testGetGameWithFilter() {

        List<Game> games = Arrays.asList(game);
        Mockito.when(service.findByModalityOrderByBeginTime(new Modality("Soccer")))
                .thenReturn(games);
        Mockito.when(service.findModality("Soccer")).thenReturn(Optional.of(new Modality("Soccer")));

        Assert.assertEquals(new ResponseEntity<>(games, HttpStatus.OK), controller.getGameTimeDesc("Soccer"));
    }

    @Test
    public void testGetGameWithoutFilter() {

        List<Game> games = Arrays.asList(game, game2);
        Mockito.when(service.findAllByOrderByBeginTime())
                .thenReturn(games);
        Mockito.when(service.findModality(null)).thenReturn(Optional.empty());

        Assert.assertEquals(new ResponseEntity<>(games, HttpStatus.OK), controller.getGameTimeDesc(null));
    }
}
