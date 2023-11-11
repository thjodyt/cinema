package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Movie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {

  Optional<Movie> findByTitle(String title);

}
