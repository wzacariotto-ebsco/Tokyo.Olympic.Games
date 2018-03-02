package com.tokyoolympicgames.manager.repository.interfaces;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamesRepository extends JpaRepository<Game, Long> {

    List<Game> findByLocal(Localization local);

    List<Game> findByModalityAndLocal(Modality modality, Localization local);

    List<Game> findByModalityOrderByBeginTime(Modality modality);

    List<Game> findAllByOrderByBeginTime();
}
