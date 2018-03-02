package com.tokyoolympicgames.manager.validator;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.exception.GameLocalModalityTimeException;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
import com.tokyoolympicgames.manager.validator.interfaces.GameValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class GameLocalModalityTimeValidatorImpl implements GameValidator {

    private GameService gameService;

    @Autowired
    public GameLocalModalityTimeValidatorImpl(GameService gameService) {

        this.gameService = gameService;
    }

    @Override
    public void validate(Game game) {

        List<Game> gameByModalityAndLocal = gameService.findByModalityAndLocal(game.getModality(), game.getLocal());

        List<Game> gamesInTheSameTime = gameByModalityAndLocal.stream()
                                                              .filter(gameByLocalAndModality ->
                                                                  gameByLocalAndModality.getBeginTime()
                                                                                        .isAfter(game.getBeginTime())
                                                                      && gameByLocalAndModality.getEndTime()
                                                                                               .isBefore(
                                                                                                   game.getEndTime()))
                                                              .collect(Collectors.toList());

        if (!gamesInTheSameTime.isEmpty())
            throw new GameLocalModalityTimeException("There is a game of the this modality happening in the same time and Local");
    }
}
