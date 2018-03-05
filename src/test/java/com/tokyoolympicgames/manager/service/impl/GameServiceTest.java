package com.tokyoolympicgames.manager.service.impl;

import com.tokyoolympicgames.manager.entity.Country;
import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;
import com.tokyoolympicgames.manager.enums.Stage;
import com.tokyoolympicgames.manager.repository.CountryRepository;
import com.tokyoolympicgames.manager.repository.GamesRepository;
import com.tokyoolympicgames.manager.repository.LocalizationRepository;
import com.tokyoolympicgames.manager.repository.ModalityRepository;
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

    @Mock
    private ModalityRepository repoM;

    @Mock
    private LocalizationRepository repoL;

    @Mock
    private CountryRepository repoC;

    @InjectMocks
    private GameServiceImpl service;

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
    public void testSave() throws Exception {

        Mockito.when(repo.save(game))
                .thenReturn(game);
        Mockito.when(repoM.save(new Modality("Soccer")))
                .thenReturn(new Modality("Soccer"));
        Mockito.when(repoL.save(new Localization("Tokyo"))).thenReturn(new Localization("Tokyo"));
        Mockito.when(repoC.save(new Country("Brazil")))
                .thenReturn(new Country("Brazil"));
        Mockito.when(repoC.save(new Country("Germany")))
                .thenReturn(new Country("Germany"));

        Assert.assertEquals(game, service.save(game));
    }

    @Test
    public void testFindByLocal() throws Exception {

        List<Game> games = Arrays.asList(game);
        Mockito.when(repo.findByLocal(new Localization("Tokyo")))
                .thenReturn(games);

        Assert.assertEquals(games, service.findByLocal(new Localization("Tokyo")));
    }

    @Test
    public void testFindByModalityAndLocal() throws Exception {

        List<Game> games = Arrays.asList(game);
        Mockito.when(repo.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")))
                .thenReturn(games);

        Assert.assertEquals(games, service.findByModalityAndLocal(new Modality("Soccer"), new Localization("Tokyo")));
    }

    @Test
    public void testFindByModalityOrderByBeginTime() throws Exception {

        List<Game> games = Arrays.asList(game2, game);
        Mockito.when(repo.findByModalityOrderByBeginTimeAsc(new Modality("Soccer")))
                .thenReturn(games);

        Assert.assertEquals(games, service.findByModalityOrderByBeginTime(new Modality("Soccer")));
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
        Assert.assertEquals(new ArrayList<>(), service.emptyGameList(new Modality("Soccer")));
        Assert.assertEquals(new ArrayList<>(), service.emptyGameList(new Modality("Soccer"), new Localization("Tokyo")));
        Assert.assertEquals(new ArrayList<>(), service.emptyGameList(new Localization("Tokyo")));
    }
}
