package com.thjodyt.cinema.service.exception;

public class HallNotFoundException extends RuntimeException {

  public HallNotFoundException(long hallId) {
    super("Hall no: " + hallId + " not found.");
  }

}
