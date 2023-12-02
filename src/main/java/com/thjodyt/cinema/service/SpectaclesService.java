package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.CreatingSpectacle;
import com.thjodyt.cinema.data.SpectacleDTO;
import com.thjodyt.cinema.data.dao.ReservationsRepository;
import com.thjodyt.cinema.data.dao.SpectaclesRepository;
import com.thjodyt.cinema.data.model.Hall;
import com.thjodyt.cinema.data.model.Movie;
import com.thjodyt.cinema.data.model.Reservation;
import com.thjodyt.cinema.data.model.Spectacle;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpectaclesService {

  private final static long ENTRANCE_TIME = 20;
  private final static long EXIT_TIME = 10;
  private final static long ADDS_TIME = 20;
  private final static long CLEANING_TIME =30;

  private final SpectaclesRepository spectaclesRepository;
  private final ReservationsRepository reservationsRepository;
  private final MoviesService moviesService;
  private final HallsService hallsService;

  public Collection<SpectacleDTO> getCurrentSpectacles() {
    return spectaclesRepository.findAllCurrent(LocalDateTime.now()).stream()
        .map(spectacle -> Mapper.map(spectacle, reservationsRepository))
        .collect(Collectors.toList());
  }

  public SpectacleDTO getSpectacle(long id) {
    return Mapper.map(
        spectaclesRepository.findCurrentById(id, LocalDateTime.now())
            .orElseThrow(SpectacleNotFoundException::new), reservationsRepository
    );
  }

  public Spectacle findById(Long id) {
    return spectaclesRepository.findById(id).orElseThrow();
  }

  public void setSpectacle(CreatingSpectacle creatingSpectacle) {
    Hall hall = hallsService.getHallById(creatingSpectacle.getHallId());
    Movie movie = moviesService.getMovieById(creatingSpectacle.getMovieId());

    LocalDateTime creatingSpectacleTimeStart = creatingSpectacle.getDate()
        .minusMinutes(ENTRANCE_TIME);
    LocalDateTime creatingSpectacleTimeEnd = creatingSpectacle.getDate()
        .plusMinutes(ADDS_TIME)
        .plusMinutes(movie.getTime())
        .plusMinutes(EXIT_TIME)
        .plusMinutes(CLEANING_TIME);

    Collection<Spectacle> conflictingSpectacles = spectaclesRepository.findConflictingSpectacles(hall, creatingSpectacleTimeStart, creatingSpectacleTimeEnd);

    if (conflictingSpectacles.isEmpty()) {
      Spectacle spectacle = new Spectacle();
      spectacle.setDate(creatingSpectacle.getDate());
      spectacle.setPrice(creatingSpectacle.getPrice());
      spectacle.setTimeStart(creatingSpectacleTimeStart);
      spectacle.setTimeEnd(creatingSpectacleTimeEnd);
      spectacle.setMovie(movie);
      spectacle.setHall(hall);
      spectaclesRepository.save(spectacle);
    } else {
      throw new HallAlreadyReservedException(hall.getSymbol());
    }
  }

  static class Mapper {

    static SpectacleDTO map(Spectacle spectacle, ReservationsRepository reservationsRepository) {
      SpectacleDTO spectacleDTO = new SpectacleDTO();
      spectacleDTO.setId(spectacle.getId());
      spectacleDTO.setDate(spectacle.getDate());
      spectacleDTO.setPrice(spectacle.getPrice());
      spectacleDTO.setTitle(spectacle.getMovie().getTitle());
      spectacleDTO.setTime(spectacle.getMovie().getTime());
      spectacleDTO.setDescription(spectacle.getMovie().getDescription());
      spectacleDTO.setHallSymbol(spectacle.getHall().getSymbol());

      List<Integer> seatsReserved = reservationsRepository.findAllBySpectacle(spectacle)
          .stream()
          .map(Reservation::getSeatNum)
          .collect(Collectors.toList());

      spectacleDTO.setSeats(new Integer[spectacle.getHall().getRows()][spectacle.getHall().getCols()]);

      for (int row = 0; row < spectacleDTO.getSeats().length; row++) {
        for (int col = 0; col < spectacleDTO.getSeats()[row].length; col++) {
          int seatNum = row * spectacleDTO.getSeats()[row].length + col + 1;
          if (!seatsReserved.contains(seatNum)) {
            spectacleDTO.getSeats()[row][col] = seatNum;
          }
        }
      }

      return spectacleDTO;
    }

  }

}
