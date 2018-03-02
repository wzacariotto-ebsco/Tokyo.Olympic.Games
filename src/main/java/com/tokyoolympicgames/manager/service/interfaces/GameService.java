package com.tokyoolympicgames.manager.service.interfaces;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Optional<Game> save(Game game);

    Optional<Game> findById(String gameId);

    List<Game> findByLocal(Localization local);

    List<Game> findByModalityAndLocal(Modality modality, Localization local);

    List<Game> findByModalityOrderByBeginTime(Modality modality);

    List<Game> findAllByOrderByBeginTime();
}
