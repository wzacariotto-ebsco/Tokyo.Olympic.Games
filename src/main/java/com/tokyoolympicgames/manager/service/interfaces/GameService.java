package com.tokyoolympicgames.manager.service.interfaces;

import com.tokyoolympicgames.manager.entity.Game;

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
     * @param game
     * @return
     */
    Optional<Game> save(Game game);

    /**
     * Find all Games in some specified location
     *
     * @param local
     * @return
     */
    List<Game> findByLocal(String local);

    /**
     * Find all games in some location from some modality
     *
     * @param modality
     * @param local
     * @return
     */
    List<Game> findByModalityAndLocal(String modality, String local);

    /**
     * Find all games from some modality ordered by first to the last based on start time
     *
     * @param modality
     * @return
     */
    List<Game> findByModalityOrderByBeginTime(String modality);

    /**
     * Find all games ordered by the first to the last based on start time
     *
     * @return
     */
    List<Game> findAllByOrderByBeginTime();
}
