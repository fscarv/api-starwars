package com.scarv.starwars.service;

import com.scarv.starwars.domain.Planet;
import com.scarv.starwars.repository.PlanetRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public Planet create(Planet planet) {
    return planetRepository.save(planet);
  }
}
