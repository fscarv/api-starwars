package com.scarv.starwars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarv.starwars.domain.Planet;
import com.scarv.starwars.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.scarv.starwars.common.PlanetConstante.PLANET;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PlanetService planetService;

  @Test
  void createPlanet_WithValidData_ReturnsCreated() throws Exception {
    when(planetService.create(PLANET)).thenReturn(PLANET);

    mockMvc.perform(post("/api/planets").content(objectMapper.writeValueAsString(PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
    Planet emptyPlanet = new Planet();
    Planet invalidPlanet = new Planet("", "", "");

    mockMvc.perform(post("/api/planets").content(objectMapper.writeValueAsString(emptyPlanet))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity());

    mockMvc.perform(post("/api/planets").content(objectMapper.writeValueAsString(invalidPlanet))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity());
  }
}
