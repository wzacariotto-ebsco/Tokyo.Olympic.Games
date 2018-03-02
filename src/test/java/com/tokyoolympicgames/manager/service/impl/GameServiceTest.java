package com.tokyoolympicgames.manager.service.impl;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.enums.Stage;
import com.tokyoolympicgames.manager.repository.GamesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Mock
    private GamesRepository repo;

    @InjectMocks
    private GameServiceImpl service;

    private Game game;

    private Game game2;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        game = new Game(LocalDateTime.now(), LocalDateTime.now()
                                                          .plusHours(3l), "Soccer", "Brazil", "Germany", "Tokyo",
            Stage.FINAL);
        game2 = new Game(LocalDateTime.now()
                                      .plusDays(1), LocalDateTime.now()
                                                                 .plusDays(1)
                                                                 .plusHours(2), "Soccer", "Spain", "USA", "Tokyo",
            Stage.FINAL);
    }

    @Test
    public void testSave() throws Exception {

        Mockito.when(repo.save(game))
               .thenReturn(game);

        Assert.assertEquals(game, service.save(game));
    }

    @Test
    public void testFindByLocal() throws Exception {

        List<Game> games = Arrays.asList(game);
        Mockito.when(repo.findByLocal("Tokyo"))
               .thenReturn(games);

        Assert.assertEquals(games, service.findByLocal("Tokyo"));
    }

    @Test
    public void testFindByModalityAndLocal() throws Exception {

        List<Game> games = Arrays.asList(game);
        Mockito.when(repo.findByModalityAndLocal("Soccer", "Tokyo"))
               .thenReturn(games);

        Assert.assertEquals(games, service.findByModalityAndLocal("Soccer", "Tokyo"));
    }

    @Test
    public void testFindByModalityOrderByBeginTime() throws Exception {

        List<Game> games = Arrays.asList(game2, game);
        Mockito.when(repo.findByModalityOrderByBeginTimeAsc("Soccer"))
               .thenReturn(games);

        Assert.assertEquals(games, service.findByModalityOrderByBeginTime("Soccer"));
    }

    @Test
    public void testFindAllByOrderByBeginTime() throws Exception {

        List<Game> games = Arrays.asList(game2, game);
        Mockito.when(repo.findAllByOrderByBeginTimeAsc())
               .thenReturn(games);

        Assert.assertEquals(games, service.findAllByOrderByBeginTime());
    }

    @Test
    public void testHystrixMethods() throws Exception {

        Assert.assertEquals(new ArrayList<>(), service.emptyGameList());
        Assert.assertEquals(new ArrayList<>(), service.emptyGameList("Soccer"));
        Assert.assertEquals(new ArrayList<>(), service.emptyGameList("Soccer", "Tokyo"));
    }
}
