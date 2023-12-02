package com.thjodyt.cinema.service.exception;

public class MovieNotFoundException extends RuntimeException {

  public MovieNotFoundException(long movieId) {
    super("Movie no: " + movieId + " not found.");
  }

}
