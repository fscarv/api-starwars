package com.scarv.starwars.service;

import com.scarv.starwars.domain.Planet;
import com.scarv.starwars.domain.QueryBuilder;
import com.scarv.starwars.repository.PlanetRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public Planet create(Planet planet) {
    return planetRepository.save(planet);
  }

  public Optional<Planet> get(Long id) {
    return planetRepository.findById(id);
  }

  public Optional<Planet> getByName(String name){
    return planetRepository.findByName(name);
  }


  public List<Planet> list(String terrain, String climate) {
    Example<Planet> query = QueryBuilder.makeQuery(new Planet(terrain, climate));
    return planetRepository.findAll(query);
  }

}
