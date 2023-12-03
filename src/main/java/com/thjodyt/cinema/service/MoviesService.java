package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.Movie;
import com.thjodyt.cinema.data.dao.MoviesRepository;
import com.thjodyt.cinema.data.model.MovieEntity;
import com.thjodyt.cinema.service.exception.MovieNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class MoviesService {

  private final MoviesRepository moviesRepository;

  public Collection<Movie> getAllMovies() {
    return moviesRepository.findAll().stream()
        .map(Mapper::map)
        .collect(Collectors.toList());
  }

  public void addMovie(Movie movie) {
    moviesRepository.save(Mapper.map(movie));
  }

  public MovieEntity getMovieById(long movieId) {
    return moviesRepository.findById(movieId)
        .orElseThrow(() -> new MovieNotFoundException(movieId));
  }

  private static class Mapper {

    public static Movie map(MovieEntity movieEntity) {
      Movie movie = new Movie();
      movie.setId(movieEntity.getId());
      movie.setTitle(HtmlUtils.htmlEscape(movieEntity.getTitle()));
      movie.setTime(movieEntity.getTime());
      movie.setDescription(HtmlUtils.htmlEscape(movieEntity.getDescription()));
      return movie;
    }

    public static MovieEntity map(Movie movie) {
      MovieEntity movieEntity = new MovieEntity();
      movieEntity.setTitle(movie.getTitle());
      movieEntity.setTime(movie.getTime());
      movieEntity.setDescription(movie.getDescription());
      return movieEntity;
    }

  }

}
