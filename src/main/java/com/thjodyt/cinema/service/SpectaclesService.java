package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.CreatingSpectacle;
import com.thjodyt.cinema.data.Spectacle;
import com.thjodyt.cinema.data.dao.SpectaclesRepository;
import com.thjodyt.cinema.data.model.HallEntity;
import com.thjodyt.cinema.data.model.MovieEntity;
import com.thjodyt.cinema.data.model.ReservationEntity;
import com.thjodyt.cinema.data.model.SpectacleEntity;
import com.thjodyt.cinema.service.exception.HallAlreadyReservedException;
import com.thjodyt.cinema.service.exception.SpectacleNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class SpectaclesService {

  private final static long ENTRANCE_TIME = 20;
  private final static long EXIT_TIME = 10;
  private final static long ADDS_TIME = 20;
  private final static long CLEANING_TIME =30;

  private final SpectaclesRepository spectaclesRepository;
  private final MoviesService moviesService;
  private final HallsService hallsService;

  public Collection<Spectacle> getCurrentSpectacles() {
    return spectaclesRepository.findAllCurrent(LocalDateTime.now()).stream()
        .map(Mapper::map)
        .collect(Collectors.toList());
  }

  public Spectacle getSpectacle(long id) {
    return Mapper.map(
        spectaclesRepository.findCurrentById(id, LocalDateTime.now())
            .orElseThrow(SpectacleNotFoundException::new)
    );
  }

  public SpectacleEntity findById(Long id) {
    return spectaclesRepository.findById(id).orElseThrow();
  }

  public void setSpectacle(CreatingSpectacle creatingSpectacle) {
    HallEntity hallEntity = hallsService.getHallById(creatingSpectacle.getHallId());
    MovieEntity movieEntity = moviesService.getMovieById(creatingSpectacle.getMovieId());

    LocalDateTime creatingSpectacleTimeStart = creatingSpectacle.getDate()
        .minusMinutes(ENTRANCE_TIME);
    LocalDateTime creatingSpectacleTimeEnd = creatingSpectacle.getDate()
        .plusMinutes(ADDS_TIME)
        .plusMinutes(movieEntity.getTime())
        .plusMinutes(EXIT_TIME)
        .plusMinutes(CLEANING_TIME);

    Collection<SpectacleEntity> conflictingSpectacleEntities = spectaclesRepository
        .findConflictingSpectacles(hallEntity, creatingSpectacleTimeStart, creatingSpectacleTimeEnd);

    if (conflictingSpectacleEntities.isEmpty()) {
      SpectacleEntity spectacleEntity = new SpectacleEntity();
      spectacleEntity.setDate(creatingSpectacle.getDate());
      spectacleEntity.setPrice(creatingSpectacle.getPrice());
      spectacleEntity.setTimeStart(creatingSpectacleTimeStart);
      spectacleEntity.setTimeEnd(creatingSpectacleTimeEnd);
      spectacleEntity.setMovieEntity(movieEntity);
      spectacleEntity.setHallEntity(hallEntity);
      spectaclesRepository.save(spectacleEntity);
    } else {
      throw new HallAlreadyReservedException(hallEntity.getSymbol());
    }
  }

  static class Mapper {

    static Spectacle map(SpectacleEntity spectacleEntity) {
      Spectacle spectacle = new Spectacle();
      spectacle.setId(spectacleEntity.getId());
      spectacle.setDate(spectacleEntity.getDate());
      spectacle.setPrice(spectacleEntity.getPrice());
      spectacle.setTitle(HtmlUtils.htmlEscape(spectacleEntity.getMovieEntity().getTitle()));
      spectacle.setTime(spectacleEntity.getMovieEntity().getTime());
      spectacle.setDescription(HtmlUtils.htmlEscape(spectacleEntity.getMovieEntity().getDescription()));
      spectacle.setHallSymbol(HtmlUtils.htmlEscape(spectacleEntity.getHallEntity().getSymbol()));

      List<Integer> seatsReserved = spectacleEntity.getReservationEntities().stream()
          .map(ReservationEntity::getSeatNum)
          .collect(Collectors.toList());

      spectacle.setSeats(new Integer[spectacleEntity.getHallEntity().getRows()][spectacleEntity.getHallEntity().getCols()]);

      for (int row = 0; row < spectacle.getSeats().length; row++) {
        for (int col = 0; col < spectacle.getSeats()[row].length; col++) {
          int seatNum = row * spectacle.getSeats()[row].length + col + 1;
          if (!seatsReserved.contains(seatNum)) {
            spectacle.getSeats()[row][col] = seatNum;
          }
        }
      }

      return spectacle;
    }

  }

}
