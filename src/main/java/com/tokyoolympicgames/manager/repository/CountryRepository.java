package com.tokyoolympicgames.manager.repository;

import com.tokyoolympicgames.manager.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
