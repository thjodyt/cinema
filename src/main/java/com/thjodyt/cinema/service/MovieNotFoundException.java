package com.thjodyt.cinema.service;

public class MovieNotFoundException extends RuntimeException {

  public MovieNotFoundException(long movieId) {
    super("Movie no: " + movieId + " not found.");
  }

}
