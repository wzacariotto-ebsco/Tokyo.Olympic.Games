package com.tokyoolympicgames.manager.repository.interfaces;

import com.tokyoolympicgames.manager.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Game, Long> {
}
