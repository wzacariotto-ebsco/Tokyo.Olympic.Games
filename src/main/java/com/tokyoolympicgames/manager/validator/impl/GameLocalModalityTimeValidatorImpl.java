package com.tokyoolympicgames.manager.validator.impl;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.exception.GameLocalModalityTimeException;
import com.tokyoolympicgames.manager.service.GameService;
import com.tokyoolympicgames.manager.validator.GameValidator;

import java.util.List;
import java.util.stream.Collectors;

public class GameLocalModalityTimeValidatorImpl implements GameValidator {

    private GameService gameService;

    public GameLocalModalityTimeValidatorImpl(GameService gameService) {

        this.gameService = gameService;
    }

    @Override
    public void validate(Game game) {

        List<Game> gameByModalityAndLocal = gameService.findByModalityAndLocal(game.getModality(), game.getLocal());

        List<Game> gamesInTheSameTime = gameByModalityAndLocal.stream()
                                                              .filter(gameByLocalAndModality -> (
                                                                  gameByLocalAndModality.getBeginTime()
                                                                                        .isAfter(game.getBeginTime())
                                                                      && gameByLocalAndModality.getEndTime()
                                                                                               .isBefore(
                                                                                                   game.getEndTime())
                                                                      || gameByLocalAndModality.getBeginTime()
                                                                                               .equals(
                                                                                                   game.getBeginTime())))
                                                              .collect(Collectors.toList());

        if (!gamesInTheSameTime.isEmpty()) {
            throw new GameLocalModalityTimeException(
                "There is a game of the this modality happening in the same time and Local");
        }
    }
}
