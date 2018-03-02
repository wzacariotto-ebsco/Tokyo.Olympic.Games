package com.tokyoolympicgames.manager.repository.interfaces;

import com.tokyoolympicgames.manager.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamesRepository extends JpaRepository<Game, Long> {

    List<Game> findByLocal(String local);

    List<Game> findByModalityAndLocal(String modality, String local);

    List<Game> findByModalityOrderByBeginTime(String modality);

    List<Game> findAllByOrderByBeginTime();
}
