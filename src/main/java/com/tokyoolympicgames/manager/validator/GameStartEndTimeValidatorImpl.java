package com.tokyoolympicgames.manager.validator;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.exception.GameTimeException;
import com.tokyoolympicgames.manager.validator.interfaces.GameValidator;

public class GameStartEndTimeValidatorImpl implements GameValidator {

    @Override
    public void validate(Game game) {

        if (game.getEndTime()
                .isBefore(game.getBeginTime()) || game.getEndTime()
                                                      .isEqual(game.getBeginTime())) {
            throw new GameTimeException("End Time should be after the Begin time");
        }
    }
}
