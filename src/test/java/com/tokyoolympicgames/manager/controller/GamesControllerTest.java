package com.tokyoolympicgames.manager.controller;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.enums.Stage;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
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
        game = new Game(LocalDateTime.now(), LocalDateTime.now().plusHours(3l), "Soccer", "Brazil", "Germany", "Tokyo", Stage.FINAL);
        game2 = new Game(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), "Basketball", "Spain", "USA", "Tokyo", Stage.FINAL);
    }


    @Test
    public void testPersistGameCorrectly() throws Exception {
        Mockito.when(service.findByModalityAndLocal("Soccer", "Tokyo")).thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal("Tokyo")).thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game)).thenReturn(Optional.of(game));

        Assert.assertEquals(new ResponseEntity<>(game, HttpStatus.CREATED), controller.persistGame(game));
    }

    @Test
    public void testPersistGameWhereThereAre4GamesInThatLocal() throws Exception {
        Mockito.when(service.findByModalityAndLocal("Soccer", "Tokyo")).thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal("Tokyo")).thenReturn(Arrays.asList(game, game, game, game));
        Mockito.when(service.save(game)).thenReturn(Optional.of(game));

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game).getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: There are 4 games in this local in the same day"), controller.persistGame(game).getBody());
    }

    @Test
    public void testPersistGameWhereTheBeginTimeIsHigherThanEndTime() throws Exception {
        game.setBeginTime(LocalDateTime.now().plusHours(1));
        game.setEndTime(LocalDateTime.now());

        Mockito.when(service.findByModalityAndLocal("Soccer", "Tokyo")).thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal("Tokyo")).thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game)).thenReturn(Optional.of(game));

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game).getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: End Time should be after the Begin time"), controller.persistGame(game).getBody());
    }

    @Test
    public void testPersistGameWhereTheDurationIsBelow30Minutes() throws Exception {
        game.setEndTime(LocalDateTime.now().plusMinutes(10));
        game.setBeginTime(LocalDateTime.now());

        Mockito.when(service.findByModalityAndLocal("Soccer", "Tokyo")).thenReturn(new ArrayList<>());
        Mockito.when(service.findByLocal("Tokyo")).thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game)).thenReturn(Optional.of(game));

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game).getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: The duration of the match is below 30 minutes"), controller.persistGame(game).getBody());
    }

    @Test
    public void testPersistGameWhereThereAnotherOneSimilarHappeningInTheSameTime() throws Exception {
        Mockito.when(service.findByModalityAndLocal("Soccer", "Tokyo")).thenReturn(Arrays.asList(game));
        Mockito.when(service.findByLocal("Tokyo")).thenReturn(Arrays.asList(game));
        Mockito.when(service.save(game)).thenReturn(Optional.of(game));

        Assert.assertEquals(HttpStatus.BAD_REQUEST, controller.persistGame(game).getStatusCode());
        Assert.assertEquals(Arrays.asList("Invalid: There is a game of the this modality happening in the same time and Local"), controller.persistGame(game).getBody());
    }

    @Test
    public void testGetGameWithFilter() {
        List<Game> games = Arrays.asList(game);
        Mockito.when(service.findByModalityOrderByBeginTime("Soccer")).thenReturn(games);

        Assert.assertEquals(new ResponseEntity<>(games, HttpStatus.OK), controller.getGameTimeDesc("Soccer"));
    }

    @Test
    public void testGetGameWithoutFilter() {
        List<Game> games = Arrays.asList(game, game2);
        Mockito.when(service.findAllByOrderByBeginTime()).thenReturn(games);

        Assert.assertEquals(new ResponseEntity<>(games, HttpStatus.OK), controller.getGameTimeDesc(null));
    }
}