package com.tokyoolympicgames.manager.validator.impl;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.exception.GameDataException;
import com.tokyoolympicgames.manager.validator.GameValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GameDataValidatorImpl implements GameValidator {

    @Override
    public void validate(Game game) {

        List<String> errors = new ArrayList<>();

        try {
            for (Field f : game.getClass()
                               .getDeclaredFields()) {
                f.setAccessible(true);
                if (!f.getName()
                      .equals("id") && (f.get(game) == null || f.get(game)
                                                                .equals(""))) {
                    errors.add(f.getName() + " can not be null or empty");
                }
            }
        } catch (IllegalAccessException e) {
            throw new GameDataException(e.getMessage());
        }

        if (!errors.isEmpty()) {
            throw new GameDataException(errors.toString());
        }
    }
}
