package com.tokyoolympicgames.manager.repository;

import com.tokyoolympicgames.manager.entity.Modality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModalityRepository extends JpaRepository<Modality, Long> {

    Modality findByName(String name);

}
