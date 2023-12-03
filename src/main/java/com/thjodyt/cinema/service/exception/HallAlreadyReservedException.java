package com.thjodyt.cinema.service.exception;

public class HallAlreadyReservedException extends RuntimeException {

  public HallAlreadyReservedException(String symbol) {
    super("Halla: \'" + symbol + "\' jest zarezerwowana w podanym terminie.");
  }

}
