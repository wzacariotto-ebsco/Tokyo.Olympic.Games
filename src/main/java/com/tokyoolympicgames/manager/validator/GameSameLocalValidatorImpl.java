package com.tokyoolympicgames.manager.validator;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.exception.GameLocalException;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
import com.tokyoolympicgames.manager.validator.interfaces.GameValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class GameSameLocalValidatorImpl implements GameValidator {

    private GameService gameService;

    @Autowired
    public GameSameLocalValidatorImpl(GameService gameService) {

        this.gameService = gameService;
    }

    @Override
    public void validate(Game game) {

        List<Game> gamesByLocal = gameService.findByLocal(game.getLocal());

        List<Game> gamesInTheSameLocal = gamesByLocal.stream()
                                                     .filter(gameByLocal -> game.getBeginTime()
                                                                                .getDayOfYear()
                                                         == (gameByLocal.getBeginTime()
                                                                        .getDayOfYear()))
                                                     .collect(Collectors.toList());
        if (gamesInTheSameLocal.size() >= 4) {
            throw new GameLocalException("There are 4 games in this local in the same day");
        }

    }

}
