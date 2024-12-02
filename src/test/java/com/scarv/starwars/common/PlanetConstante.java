package com.scarv.starwars.common;

import com.scarv.starwars.domain.Planet;

public class PlanetConstante {

  public static final Planet PLANET = new Planet("Tatooine", "arid", "desert");
  public static final Planet INVALID_PLANET = new Planet("", "", "");
}
