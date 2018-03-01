package com.tokyoolympicgames.manager.service;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.repository.interfaces.GamesRepository;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public Game findById(String gameId) {
        return this.gamesRepository.findOne(Long.valueOf(gameId));
    }

}
