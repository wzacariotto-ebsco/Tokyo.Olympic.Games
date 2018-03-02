package com.tokyoolympicgames.manager.service;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;
import com.tokyoolympicgames.manager.repository.interfaces.GamesRepository;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Scope("prototype")
public class GameServiceImpl implements GameService {

    private GamesRepository gamesRepository;

    @Autowired
    public GameServiceImpl(GamesRepository gamesRepository) {

        this.gamesRepository = gamesRepository;
    }

    @Override
    public Optional<Game> save(Game game) {

        return Optional.ofNullable(this.gamesRepository.save(game));
    }

    @Override
    public Optional<Game> findById(String gameId) {

        return Optional.ofNullable(this.gamesRepository.findOne(Long.valueOf(gameId)));
    }

    @Override
    public List<Game> findByLocal(Localization local) {

        return this.gamesRepository.findByLocal(local);
    }

    @Override
    public List<Game> findByModalityAndLocal(Modality modality, Localization local) {

        return this.gamesRepository.findByModalityAndLocal(modality, local);
    }

    @Override
    public List<Game> findByModalityOrderByBeginTime(Modality modality) {

        return this.gamesRepository.findByModalityOrderByBeginTime(modality);
    }

    @Override
    public List<Game> findAllByOrderByBeginTime() {

        return this.gamesRepository.findAllByOrderByBeginTime();
    }

}
