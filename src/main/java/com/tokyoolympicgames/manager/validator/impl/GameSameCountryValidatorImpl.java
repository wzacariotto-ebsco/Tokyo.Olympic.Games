package com.tokyoolympicgames.manager.validator.impl;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.enums.Stage;
import com.tokyoolympicgames.manager.exception.GameCountryException;
import com.tokyoolympicgames.manager.validator.GameValidator;

public class GameSameCountryValidatorImpl implements GameValidator {

    @Override
    public void validate(Game game) {

        Stage stage = game.getStage();
        if (stage.equals(Stage.SEMIFINAL) || stage.equals(Stage.FINAL)) {
            return;
        }
        if (game.getFirstCountry()
                .equals(game.getSecondCountry())) {
            throw new GameCountryException("Using the same country in a not allowed in " + stage + " Stage");
        }
    }
}
