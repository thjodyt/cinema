package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.ReservationDTO;
import com.thjodyt.cinema.data.ReservationDetails;
import com.thjodyt.cinema.data.dao.ReservationsRepository;
import com.thjodyt.cinema.data.model.Reservation;
import com.thjodyt.cinema.data.model.Spectacle;
import com.thjodyt.cinema.data.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationsService {

  private final ReservationsRepository reservationsRepository;

  public void reserve(ReservationDetails reservationDetails, User user, Spectacle spectacle) {
    for (Integer seat : reservationDetails.getSeats()) {
      if (reservationsRepository.findBySpectacleAndSeatNum(spectacle, seat).isEmpty()) {
        reservationsRepository.save(createReservation(spectacle, user.getId(), seat));
      }
    }
  }

  public Collection<ReservationDTO> getReservations(Long usersId) {
    return Mapper.map(reservationsRepository.findAllByUsersId(usersId));
  }

  private Reservation createReservation(Spectacle spectacle, long userId, int seat) {
    Reservation reservation = new Reservation();
    reservation.setSpectacle(spectacle);
    reservation.setSeatNum(seat);
    reservation.setUsersId(userId);
    return reservation;
  }

  static class Mapper {

    static Collection<ReservationDTO> map(Collection<Reservation> reservations) {
      List<ReservationDTO> result = new ArrayList<>();
      for (Reservation reservation : reservations) {
        ReservationDTO dto = new ReservationDTO();
        dto.setSpectacleId(reservation.getSpectacle().getId());
        if (result.contains(dto)) {
          result.get(result.indexOf(dto)).getSeats().add(reservation.getSeatNum());
        } else {
          dto.setDate(reservation.getSpectacle().getDate());
          dto.setPrice(reservation.getSpectacle().getPrice());
          dto.setTitle(reservation.getSpectacle().getMovie().getTitle());
          dto.setTime(reservation.getSpectacle().getMovie().getTime());
          dto.setDescription(reservation.getSpectacle().getMovie().getDescription());
          dto.setHallSymbol(reservation.getSpectacle().getHall().getSymbol());
          dto.setSeats(new ArrayList<>());
          dto.getSeats().add(reservation.getSeatNum());
          result.add(dto);
        }
      }
      return result;
    }
  }

}
