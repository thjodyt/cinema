package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Reservation;
import com.thjodyt.cinema.data.model.ReservationId;
import com.thjodyt.cinema.data.model.Spectacle;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, ReservationId> {

  Collection<Reservation> findAllBySpectacle(Spectacle spectacle);

  Optional<Reservation> findBySpectacleAndSeatNum(Spectacle spectacle, int seat);

  @Query("""
select r from Reservation r
left join fetch r.spectacle s
left join fetch s.movie
left join fetch s.hall
where r.usersId = ?1
order by s.date
""")
  Collection<Reservation> findAllByUsersId(long usersId);

}
