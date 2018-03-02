package com.tokyoolympicgames.manager.util;

import com.tokyoolympicgames.manager.service.interfaces.GameService;
import com.tokyoolympicgames.manager.validator.GameDurationValidatorImpl;
import com.tokyoolympicgames.manager.validator.GameLocalModalityTimeValidatorImpl;
import com.tokyoolympicgames.manager.validator.GameSameLocalValidatorImpl;
import com.tokyoolympicgames.manager.validator.GameStartEndTimeValidatorImpl;
import com.tokyoolympicgames.manager.validator.interfaces.GameChainValidator;

/**
 * Chain helper factory
 *
 * @author Wendler Zacariotto
 */
public class ChainHelper {

    /**
     * Method for creating the Game Chain for validation
     *
     * @param service
     * @return
     */
    public static GameChainValidator getGameChainValidator(GameService service) {

        GameChainValidator gameChainValidator = new GameChainValidator();
        gameChainValidator.addValidator(new GameDurationValidatorImpl());
        gameChainValidator.addValidator(new GameStartEndTimeValidatorImpl());
        gameChainValidator.addValidator(new GameLocalModalityTimeValidatorImpl(service));
        gameChainValidator.addValidator(new GameSameLocalValidatorImpl(service));
        return gameChainValidator;
    }
}
