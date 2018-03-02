package com.tokyoolympicgames.manager.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.repository.GamesRepository;
import com.tokyoolympicgames.manager.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for using JPA repository
 *
 * @author Wendler
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private GamesRepository gamesRepository;

    @Autowired
    public GameServiceImpl(GamesRepository gamesRepository) {

        this.gamesRepository = gamesRepository;
    }

    @Override
    public Game save(Game game) {

        return this.gamesRepository.save(game);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findByLocal(String local) {

        return this.gamesRepository.findByLocal(local);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findByModalityAndLocal(String modality, String local) {

        return this.gamesRepository.findByModalityAndLocal(modality, local);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findByModalityOrderByBeginTime(String modality) {

        return this.gamesRepository.findByModalityOrderByBeginTimeAsc(modality);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findAllByOrderByBeginTime() {

        return this.gamesRepository.findAllByOrderByBeginTimeAsc();
    }

    public List<Game> emptyGameList() {

        return new ArrayList<>();
    }

    public List<Game> emptyGameList(String modality) {

        return new ArrayList<>();
    }

    public List<Game> emptyGameList(String modality, String local) {

        return new ArrayList<>();
    }

}
