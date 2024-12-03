package com.scarv.starwars.repository;

import com.scarv.starwars.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.scarv.starwars.common.PlanetConstante.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class PlanetRepositoryTest {

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private TestEntityManager entityManager;

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
  void createPlanet_WithInvalidDAta_ThrowsException(){
    Planet emptyPlanet = new Planet();
    Planet invalidPlanet = new Planet("", "", "");

    assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
    assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
  }
}
