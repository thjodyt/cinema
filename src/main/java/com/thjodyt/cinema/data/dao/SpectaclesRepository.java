package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Hall;
import com.thjodyt.cinema.data.model.Movie;
import com.thjodyt.cinema.data.model.Spectacle;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectaclesRepository extends JpaRepository<Spectacle, Long> {

  Collection<Spectacle> findByMovieAndHallAndDate(Movie movie, Hall hall, LocalDateTime dateTime);
  @Query("select s from Spectacle s left join fetch s.movie left join fetch s.hall order by s.date")
  Collection<Spectacle> findAllCurrent(LocalDateTime dateTime);

}
