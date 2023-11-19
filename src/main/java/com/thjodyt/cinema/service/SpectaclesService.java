package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.ReservationDTO;
import com.thjodyt.cinema.data.SpectacleDTO;
import com.thjodyt.cinema.data.dao.ReservationsRepository;
import com.thjodyt.cinema.data.dao.SpectaclesRepository;
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

  private final SpectaclesRepository spectaclesRepository;
  private final ReservationsRepository reservationsRepository;

  public Collection<SpectacleDTO> getCurrentSpectacles() {
    return spectaclesRepository.findAllCurrent(LocalDateTime.now()).stream()
        .map(spectacle -> Mapper.map(spectacle, reservationsRepository))
        .collect(Collectors.toList());
  }

  static class Mapper {

    static SpectacleDTO map(Spectacle spectacle, ReservationsRepository reservationsRepository) {
      SpectacleDTO spectacleDTO = new SpectacleDTO();
      spectacleDTO.setId(spectacle.getId());
      spectacleDTO.setDate(spectacle.getDate());
      spectacleDTO.setPrice(spectacle.getPrice());
      spectacleDTO.setTitle(spectacle.getMovie().getTitle());
      spectacleDTO.setDescription(spectacle.getMovie().getDescription());
      spectacleDTO.setHallSymbol(spectacle.getHall().getSymbol());

      List<Integer> seatsReserved = reservationsRepository.findAllBySpectaclesId(spectacle.getId())
          .stream()
          .map(Reservation::getSeatNum)
          .collect(Collectors.toList());
      spectacleDTO.setReservationDto(new ReservationDTO[spectacle.getHall().getRows()][spectacle.getHall().getCols()]);

      for (int row = 0; row < spectacleDTO.getReservationDto().length; row++) {
        for (int col = 0; col < spectacleDTO.getReservationDto()[row].length; col++) {
          int seatNum = row * spectacleDTO.getReservationDto()[row].length + col + 1;
          if (seatsReserved.contains(seatNum)) {
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setSpectaclesId(spectacle.getId());
            reservationDTO.setSeatNum(seatNum);
            spectacleDTO.getReservationDto()[row][col] = reservationDTO;
          }
        }
      }

      return spectacleDTO;
    }

  }

}
