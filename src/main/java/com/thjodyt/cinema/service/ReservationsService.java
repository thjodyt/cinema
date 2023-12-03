package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.Reservation;
import com.thjodyt.cinema.data.ReservationDetails;
import com.thjodyt.cinema.data.dao.ReservationsRepository;
import com.thjodyt.cinema.data.model.ReservationEntity;
import com.thjodyt.cinema.data.model.SpectacleEntity;
import com.thjodyt.cinema.data.model.UserEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class ReservationsService {

  private final ReservationsRepository reservationsRepository;

  public void reserve(ReservationDetails reservationDetails, UserEntity userEntity, SpectacleEntity spectacleEntity) {
    for (Integer seat : reservationDetails.getSeats()) {
      if (reservationsRepository.findBySpectacleEntityAndSeatNum(spectacleEntity, seat).isEmpty()) {
        reservationsRepository.save(createReservation(spectacleEntity, userEntity.getId(), seat));
      }
    }
  }

  public Collection<Reservation> getReservations(Long usersId) {
    return Mapper.map(reservationsRepository.findAllByUsersId(usersId));
  }

  private ReservationEntity createReservation(SpectacleEntity spectacleEntity, long userId, int seat) {
    ReservationEntity reservationEntity = new ReservationEntity();
    reservationEntity.setSpectacleEntity(spectacleEntity);
    reservationEntity.setSeatNum(seat);
    reservationEntity.setUsersId(userId);
    return reservationEntity;
  }

  static class Mapper {

    static Collection<Reservation> map(Collection<ReservationEntity> reservationEntities) {
      List<Reservation> result = new ArrayList<>();
      for (ReservationEntity reservationEntity : reservationEntities) {
        Reservation reservation = new Reservation();
        reservation.setSpectacleId(reservationEntity.getSpectacleEntity().getId());
        if (result.contains(reservation)) {
          result.get(result.indexOf(reservation)).getSeats().add(reservationEntity.getSeatNum());
        } else {
          reservation.setDate(reservationEntity.getSpectacleEntity().getDate());
          reservation.setPrice(reservationEntity.getSpectacleEntity().getPrice());
          reservation.setTitle(HtmlUtils.htmlEscape(reservationEntity.getSpectacleEntity().getMovieEntity().getTitle()));
          reservation.setTime(reservationEntity.getSpectacleEntity().getMovieEntity().getTime());
          reservation.setDescription(HtmlUtils.htmlEscape(reservationEntity.getSpectacleEntity().getMovieEntity().getDescription()));
          reservation.setHallSymbol(HtmlUtils.htmlEscape(reservationEntity.getSpectacleEntity().getHallEntity().getSymbol()));
          reservation.setSeats(new ArrayList<>());
          reservation.getSeats().add(reservationEntity.getSeatNum());
          result.add(reservation);
        }
      }
      return result;
    }
  }

}
