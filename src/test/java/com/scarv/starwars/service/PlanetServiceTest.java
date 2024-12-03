package com.scarv.starwars.service;

import com.scarv.starwars.domain.Planet;
import com.scarv.starwars.domain.QueryBuilder;
import com.scarv.starwars.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.scarv.starwars.common.PlanetConstante.PLANET;
import static com.scarv.starwars.common.PlanetConstante.INVALID_PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

  @InjectMocks
  private PlanetService planetService;

  @Mock
  private PlanetRepository planetRepository;

  @Test
  void createPlanet_WithValidData_ReturnsPlanet(){
    when(planetRepository.save(PLANET)).thenReturn(PLANET);

    Planet sut = planetService.create(PLANET);

    assertThat(sut).isEqualTo(PLANET);
  }

  @Test
  void createPlanet_WithInavalidData_ReturnsException(){
    when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
  }

  @Test
  void getPlanet_ByExistingId_ReturnsPlanet(){
    when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET));

    Optional<Planet> sut = planetService.get(1L);

    assertThat(sut).isNotEmpty();
    assertThat(sut).contains(PLANET);
  }

  @Test
  void getPlanet_ByUnexistingId_ReturnsEmpty(){
    when(planetRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<Planet> sut = planetService.get(1L);

    assertThat(sut).isEmpty();
  }

  @Test
  void getPlanet_ByExistingName_ReturnsPlanet(){
    when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

    Optional<Planet> sut = planetService.getByName(PLANET.getName());

    assertThat(sut).isNotEmpty();
    assertThat(sut).isEqualTo(Optional.of(PLANET));
  }

  @Test
  void getPlanet_ByUnexistingName_ReturnsEmpty(){
    when(planetRepository.findByName(INVALID_PLANET.getName())).thenReturn(Optional.empty());

    Optional<Planet> sut = planetService.getByName(INVALID_PLANET.getName());

    assertThat(sut).isEmpty();
  }

  @Test
  void listPlanets_ReturnsAllPlanets(){
    List<Planet> planets = new ArrayList<>() {{
      add(PLANET);
    }};

    Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getTerrain(), PLANET.getClimate()));
    when(planetRepository.findAll(query)).thenReturn(planets);

    List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

    assertThat(sut).isNotEmpty();
    assertThat(sut).hasSize(1);
    assertThat(sut.get(0)).isEqualTo(PLANET);
  }

  @Test
  void listPlanets_ReturnsNoPlanets(){
    when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

    List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

    assertThat(sut).isEmpty();
  }

  @Test
  void removePlanet_WithExistingId_doesNotThrowException(){
    doNothing().when(planetRepository).deleteById(1L);

    planetService.remove(1L);

    verify(planetRepository, times(1)).deleteById(1L);
  }

  @Test
  void removePlanet_WithUnexistingId_throwsException(){
    doThrow(RuntimeException.class).when(planetRepository).deleteById(100L);

    assertThatThrownBy(() -> planetService.remove(100L)).isInstanceOf(RuntimeException.class);
  }
}
