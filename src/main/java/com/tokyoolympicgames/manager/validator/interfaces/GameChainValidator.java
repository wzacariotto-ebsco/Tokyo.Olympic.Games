package com.tokyoolympicgames.manager.validator.interfaces;

import com.tokyoolympicgames.manager.entity.Game;

import java.util.LinkedList;

/**
 * The idea here is that the chain validation.
 * This class is based on chain of responsibility pattern.
 *
 * @author Wendler Zacariotto
 */

public class GameChainValidator {

    LinkedList<GameValidator> chain;

    public GameChainValidator() {

        chain = new LinkedList<>();
    }

    public boolean hasValidator() {

        return !chain.isEmpty();
    }

    public void addValidator(GameValidator validator) {

        chain.addLast(validator);
    }

    /**
     * If there's Validators in the chain then proceed with validation else return true indicating that the validation did succeed.
     *
     * @param game
     */
    public void doValidate(Game game) {

        if (hasValidator()) {
            GameValidator next = chain.removeFirst();
            next.validate(game);
        }
    }
}
