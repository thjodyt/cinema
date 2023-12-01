package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.MovieDTO;
import com.thjodyt.cinema.data.dao.MoviesRepository;
import com.thjodyt.cinema.data.model.Movie;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoviesService {

  private final MoviesRepository moviesRepository;

  public Collection<MovieDTO> getAllMovies() {
    return moviesRepository.findAll().stream()
        .map(Mapper::map)
        .collect(Collectors.toList());
  }

  public void addMovie(MovieDTO movieDTO) {
    moviesRepository.save(Mapper.map(movieDTO));
  }

  private static class Mapper {

    public static MovieDTO map(Movie movie) {
      MovieDTO movieDTO = new MovieDTO();
      movieDTO.setId(movie.getId());
      movieDTO.setTitle(movie.getTitle());
      movieDTO.setTime(movie.getTime());
      movieDTO.setDescription(movie.getDescription());
      return movieDTO;
    }

    public static Movie map(MovieDTO movieDTO) {
      Movie movie = new Movie();
      movie.setTitle(movieDTO.getTitle());
      movie.setTime(movieDTO.getTime());
      movie.setDescription(movieDTO.getDescription());
      return movie;
    }

  }

}
