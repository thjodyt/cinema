package com.thjodyt.cinema.service.exception;

public class SpectacleNotFoundException extends RuntimeException {

  public SpectacleNotFoundException() {
    super("Spectacle not found.");
  }

}
