package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Spectacle;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectaclesRepository extends JpaRepository<Spectacle, Long> {

  Collection<Spectacle> findByMoviesIdAndHallsIdAndDate(Long moviesId, Long hallsId, LocalDateTime dateTime);

}
