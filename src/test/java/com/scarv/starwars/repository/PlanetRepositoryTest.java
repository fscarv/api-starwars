package com.scarv.starwars.repository;

import com.scarv.starwars.domain.Planet;
import org.h2.table.Plan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.scarv.starwars.common.PlanetConstante.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class PlanetRepositoryTest {

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private TestEntityManager entityManager;

  @AfterEach
  public void afterEach() {
    PLANET.setId(null);
  }

  @Test
  void createPlanet_WithValidData_ReturnsPlanet(){
    Planet planet = planetRepository.save(PLANET);

    Planet sut = entityManager.find(Planet.class, planet.getId());

    assertThat(sut).isNotNull();
    assertThat(sut.getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
  }

  @Test
  void createPlanet_WithInvalidData_ThrowsException(){
    Planet emptyPlanet = new Planet();
    Planet invalidPlanet = new Planet("", "", "");

    assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
    assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
  }

  @Test
  void createPlanet_WithExistingName_ThrowsException(){
    Planet planet = entityManager.persistFlushFind(PLANET);
    entityManager.detach(planet);
    planet.setId(null);

    assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
  }

  @Test
  void getPlanet_ByExistingId_ReturnsPlanet() {
    Planet planet = entityManager.persistFlushFind(PLANET);

    Optional<Planet> planetOptional = planetRepository.findById(planet.getId());

    assertThat(planetOptional).isNotEmpty();
    assertThat(planetOptional.get()).isEqualTo(planet);
  }

  @Test
  void getPlanet_ByUnexistingId_ReturnsEmpty() {
    Optional<Planet> planetOptional = planetRepository.findById(1L);

    assertThat(planetOptional).isEmpty();
  }
}
