package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.dao.HallsRepository;
import com.thjodyt.cinema.data.dao.MoviesRepository;
import com.thjodyt.cinema.data.dao.SpectaclesRepository;
import com.thjodyt.cinema.data.model.Hall;
import com.thjodyt.cinema.data.model.Movie;
import com.thjodyt.cinema.data.model.Spectacle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BootstrapDatabase {

  private final static String HALL_A = "HALL A";
  private final static String HALL_B = "HALL B";
  private final static String MOVIE_1 = "Nullam venenatis";
  private final static String DESCRIPTION_1 = "Nullam venenatis, nulla sit amet elementum convallis, leo odio scelerisque enim, ut pellentesque arcu sem ac nunc. Etiam mattis neque sit amet ipsum mollis, nec porttitor erat tempor.";
  private final static String MOVIE_2 = "Praesent vitae nisi nulla";
  private final static String DESCRIPTION_2 = "Praesent vitae nisi nulla. Pellentesque blandit malesuada nulla non porttitor. Aliquam erat volutpat. Quisque at posuere quam. Integer lobortis urna a lectus malesuada, in pretium dolor viverra.";

  private final HallsRepository hallsRepository;
  private final MoviesRepository moviesRepository;
  private final SpectaclesRepository spectaclesRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void bootstrap() {
    createHall(HALL_A, 20, 20);
    createHall(HALL_B, 25, 25);

    createMovie(MOVIE_1, DESCRIPTION_1);
    createMovie(MOVIE_2, DESCRIPTION_2);

    createSpectacle(MOVIE_1, HALL_A);
    createSpectacle(MOVIE_2, HALL_B);
  }

  private void createSpectacle(String title, String hallCode) {
    Movie movie = moviesRepository.findByTitle(title).orElseThrow();
    Hall hall = hallsRepository.findBySymbol(hallCode).orElseThrow();
    for (int i = 1; i < 6; i++) {
      LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().plusDays(i), LocalTime.of(10, 00));
      if (spectaclesRepository.findByMovieAndHallAndDate(movie, hall, dateTime).isEmpty()) {
        Spectacle spectacle = new Spectacle();
        spectacle.setDate(dateTime);
        spectacle.setPrice(50);
        spectacle.setMovie(movie);
        spectacle.setHall(hall);
        spectaclesRepository.save(spectacle);
      }
    }
  }

  private void createMovie(String title, String description) {
    if (moviesRepository.findByTitle(title).isEmpty()) {
      Movie movie = new Movie();
      movie.setTitle(title);
      movie.setDescription(description);
      moviesRepository.save(movie);
    }
  }

  private void createHall(String hallSymbol, int cols, int rows) {
    if (hallsRepository.findBySymbol(hallSymbol).isEmpty()) {
      Hall hall = new Hall();
      hall.setSymbol(hallSymbol);
      hall.setCols(cols);
      hall.setRows(rows);
      hallsRepository.save(hall);
    }
  }

}
