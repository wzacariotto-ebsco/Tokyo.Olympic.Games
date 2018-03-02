package com.tokyoolympicgames.manager.util;

import com.tokyoolympicgames.manager.service.GameService;
import com.tokyoolympicgames.manager.validator.GameChainValidator;
import com.tokyoolympicgames.manager.validator.impl.GameDataValidatorImpl;
import com.tokyoolympicgames.manager.validator.impl.GameDurationValidatorImpl;
import com.tokyoolympicgames.manager.validator.impl.GameLocalModalityTimeValidatorImpl;
import com.tokyoolympicgames.manager.validator.impl.GameSameCountryValidatorImpl;
import com.tokyoolympicgames.manager.validator.impl.GameSameLocalValidatorImpl;
import com.tokyoolympicgames.manager.validator.impl.GameStartEndTimeValidatorImpl;

/**
 * Chain helper factory
 *
 * @author Wendler Zacariotto
 */
public class ValidatorHelper {

    /**
     * Method for creating the Game Chain for validation
     *
     * @param service
     * @return
     */
    public static GameChainValidator getGameChainFullValidator(GameService service) {

        GameChainValidator gameChainValidator = new GameChainValidator();
        gameChainValidator.addValidator(new GameDataValidatorImpl());
        gameChainValidator.addValidator(new GameDurationValidatorImpl());
        gameChainValidator.addValidator(new GameStartEndTimeValidatorImpl());
        gameChainValidator.addValidator(new GameLocalModalityTimeValidatorImpl(service));
        gameChainValidator.addValidator(new GameSameLocalValidatorImpl(service));
        gameChainValidator.addValidator(new GameSameCountryValidatorImpl());
        return gameChainValidator;
    }

}
