package com.tokyoolympicgames.manager.service.interfaces;

import com.tokyoolympicgames.manager.entity.Game;

public interface GameService {

    Game save(Game game);

    Game findById(String gameId);
}
