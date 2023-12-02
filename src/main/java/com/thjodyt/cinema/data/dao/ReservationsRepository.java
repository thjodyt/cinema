package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.ReservationEntity;
import com.thjodyt.cinema.data.model.ReservationId;
import com.thjodyt.cinema.data.model.SpectacleEntity;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<ReservationEntity, ReservationId> {

  Optional<ReservationEntity> findBySpectacleEntityAndSeatNum(SpectacleEntity spectacleEntity, int seat);

  @Query("""
select r from ReservationEntity r
left join fetch r.spectacleEntity s
left join fetch s.movieEntity
left join fetch s.hallEntity
where r.usersId = ?1
order by s.date
""")
  Collection<ReservationEntity> findAllByUsersId(long usersId);

}
