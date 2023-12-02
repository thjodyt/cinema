package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Hall;
import com.thjodyt.cinema.data.model.Movie;
import com.thjodyt.cinema.data.model.Spectacle;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectaclesRepository extends JpaRepository<Spectacle, Long> {

  Collection<Spectacle> findByMovieAndHallAndDate(Movie movie, Hall hall, LocalDateTime dateTime);
  @Query("select s from Spectacle s left join fetch s.movie left join fetch s.hall where s.date >= ?1 order by s.date")
  Collection<Spectacle> findAllCurrent(LocalDateTime dateTime);
  @Query("select s from Spectacle s left join fetch s.movie left join fetch s.hall where s.id = ?1 and s.date >= ?2")
  Optional<Spectacle> findCurrentById(long id, LocalDateTime dateTime);
  @Query("""
select s from Spectacle s
left join fetch s.movie
where s.hall = ?1
and (s.timeStart between ?2 and ?3 or s.timeEnd between ?2 and ?3)
""")
  Collection<Spectacle> findConflictingSpectacles(Hall hall, LocalDateTime creatingSpectacleTimeStart, LocalDateTime creatingSpectacleTimeEnd);
}
