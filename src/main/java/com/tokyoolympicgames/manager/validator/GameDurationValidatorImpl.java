package com.tokyoolympicgames.manager.validator;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.exception.GameTimeException;
import com.tokyoolympicgames.manager.validator.interfaces.GameValidator;

import java.time.Duration;

public class GameDurationValidatorImpl implements GameValidator {

    @Override
    public void validate(Game game) {

        if (Duration.between(game.getBeginTime(), game.getEndTime())
                    .toMinutes() < 30) {
            throw new GameTimeException("The duration of the match is below 30 minutes");
        }
    }
}
