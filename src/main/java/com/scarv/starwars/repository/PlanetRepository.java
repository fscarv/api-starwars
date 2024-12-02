package com.scarv.starwars.repository;

import com.scarv.starwars.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Long> {

  Optional<Planet> findByName(String name);
}
