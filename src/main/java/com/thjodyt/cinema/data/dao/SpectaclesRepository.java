package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.HallEntity;
import com.thjodyt.cinema.data.model.SpectacleEntity;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectaclesRepository extends JpaRepository<SpectacleEntity, Long> {

  @Query("""
select s from SpectacleEntity s
left join fetch s.movieEntity
left join fetch s.hallEntity
left join fetch s.reservationEntities
where s.date >= ?1
order by s.date
""")
  Collection<SpectacleEntity> findAllCurrent(LocalDateTime dateTime);

  @Query("""
select s from SpectacleEntity s
left join fetch s.movieEntity
left join fetch s.hallEntity
left join fetch s.reservationEntities
where s.id = ?1 and s.date >= ?2
""")
  Optional<SpectacleEntity> findCurrentById(long id, LocalDateTime dateTime);

  @Query("""
select s from SpectacleEntity s
left join fetch s.movieEntity
where s.hallEntity = ?1
and (s.timeStart between ?2 and ?3 or s.timeEnd between ?2 and ?3)
""")
  Collection<SpectacleEntity> findConflictingSpectacles(HallEntity hallEntity, LocalDateTime creatingSpectacleTimeStart, LocalDateTime creatingSpectacleTimeEnd);

}
