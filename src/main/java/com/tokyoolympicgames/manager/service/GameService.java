package com.tokyoolympicgames.manager.service;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;

import java.util.List;
import java.util.Optional;

/**
 * Service for accessing the data base
 *
 * @author Wendler
 */
public interface GameService {

    /**
     * Save a Game
     *
     * @param game To persist game
     * @return optional from persisted game
     */
    Game save(Game game);

    /**
     * Find all Games in some specified location
     *
     * @param local game local
     * @return list of games by local
     */
    List<Game> findByLocal(Localization local);

    /**
     * Find all games in some location from some modality
     *
     * @param modality game modality
     * @param local    game local
     * @return
     */
    List<Game> findByModalityAndLocal(Modality modality, Localization local);

    /**
     * Find all games from some modality ordered by first to the last based on start time
     *
     * @param modality
     * @return
     */
    List<Game> findByModalityOrderByBeginTime(Modality modality);

    /**
     * Find all games ordered by the first to the last based on start time
     *
     * @return
     */
    List<Game> findAllByOrderByBeginTime();

    /**
     *
     * Method to find Modality by string
     * @param modality
     * @return
     */
    Optional<Modality> findModality(String modality);
}
