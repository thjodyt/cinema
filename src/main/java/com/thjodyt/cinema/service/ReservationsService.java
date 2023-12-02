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
        Reservation dto = new Reservation();
        dto.setSpectacleId(reservationEntity.getSpectacleEntity().getId());
        if (result.contains(dto)) {
          result.get(result.indexOf(dto)).getSeats().add(reservationEntity.getSeatNum());
        } else {
          dto.setDate(reservationEntity.getSpectacleEntity().getDate());
          dto.setPrice(reservationEntity.getSpectacleEntity().getPrice());
          dto.setTitle(reservationEntity.getSpectacleEntity().getMovieEntity().getTitle());
          dto.setTime(reservationEntity.getSpectacleEntity().getMovieEntity().getTime());
          dto.setDescription(reservationEntity.getSpectacleEntity().getMovieEntity().getDescription());
          dto.setHallSymbol(reservationEntity.getSpectacleEntity().getHallEntity().getSymbol());
          dto.setSeats(new ArrayList<>());
          dto.getSeats().add(reservationEntity.getSeatNum());
          result.add(dto);
        }
      }
      return result;
    }
  }

}
