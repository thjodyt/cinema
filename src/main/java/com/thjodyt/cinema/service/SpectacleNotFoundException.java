package com.thjodyt.cinema.service;

public class SpectacleNotFoundException extends RuntimeException {

  public SpectacleNotFoundException() {
    super("Spectacle not found.");
  }

}
