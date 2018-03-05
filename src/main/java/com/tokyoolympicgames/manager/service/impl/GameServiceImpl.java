package com.tokyoolympicgames.manager.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tokyoolympicgames.manager.entity.Country;
import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Localization;
import com.tokyoolympicgames.manager.entity.Modality;
import com.tokyoolympicgames.manager.repository.CountryRepository;
import com.tokyoolympicgames.manager.repository.GamesRepository;
import com.tokyoolympicgames.manager.repository.LocalizationRepository;
import com.tokyoolympicgames.manager.repository.ModalityRepository;
import com.tokyoolympicgames.manager.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for using JPA repository
 *
 * @author Wendler
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private GamesRepository gamesRepository;
    private CountryRepository contryRepository;
    private LocalizationRepository localizationRepository;
    private ModalityRepository modalityRepository;

    @Autowired
    public GameServiceImpl(GamesRepository gamesRepository, CountryRepository contryRepository, LocalizationRepository localizationRepository, ModalityRepository modalityRepository) {

        this.contryRepository = contryRepository;
        this.gamesRepository = gamesRepository;
        this.localizationRepository = localizationRepository;
        this.modalityRepository = modalityRepository;
    }

    @Override
    public Game save(Game game) {

        this.contryRepository.save(game.getSecondCountry());
        this.contryRepository.save(game.getFirstCountry());
        this.localizationRepository.save(game.getLocal());
        this.modalityRepository.save(game.getModality());
        return this.gamesRepository.save(game);
    }

    @Override
    public Optional<Modality> findModality(String modality) {
        return Optional.ofNullable(this.modalityRepository.findByName(modality));
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findByLocal(Localization local) {

        return this.gamesRepository.findByLocal(local);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findByModalityAndLocal(Modality modality, Localization local) {

        return this.gamesRepository.findByModalityAndLocal(modality, local);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findByModalityOrderByBeginTime(Modality modality) {

        return this.gamesRepository.findByModalityOrderByBeginTimeAsc(modality);
    }

    @Override
    @HystrixCommand(fallbackMethod = "emptyGameList")
    public List<Game> findAllByOrderByBeginTime() {

        return this.gamesRepository.findAllByOrderByBeginTimeAsc();
    }

    public List<Game> emptyGameList() {

        return new ArrayList<>();
    }

    public List<Game> emptyGameList(Modality modality) {

        return new ArrayList<>();
    }

    public List<Game> emptyGameList(Localization local) {

        return new ArrayList<>();
    }
    
    public List<Game> emptyGameList(Modality modality, Localization local) {

        return new ArrayList<>();
    }

}
