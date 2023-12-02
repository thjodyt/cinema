package com.thjodyt.cinema.service;

public class HallAlreadyReservedException extends RuntimeException {

  public HallAlreadyReservedException(String symbol) {
    super("Hall: " + symbol + " already reserved.");
  }

}
