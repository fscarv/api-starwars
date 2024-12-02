package com.scarv.starwars.controller;

import com.scarv.starwars.domain.Planet;
import com.scarv.starwars.service.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

  private final PlanetService planetService;

  public PlanetController(PlanetService planetService) {
    this.planetService = planetService;
  }

  @PostMapping
  public ResponseEntity<Planet> createPlanet(@RequestBody Planet planet) {
    Planet planetCreated = planetService.create(planet);
    return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
    return planetService.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Planet> getPlanetByName(@PathVariable("name") String name) {
    return planetService.getByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Planet>> listAll(@RequestParam(required = false) String terrain,
                                              @RequestParam(required = false) String climate) {
    return ResponseEntity.ok(planetService.list(terrain, climate));
  }
}
