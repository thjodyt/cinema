package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Reservation;
import com.thjodyt.cinema.data.model.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, ReservationId> {

}
