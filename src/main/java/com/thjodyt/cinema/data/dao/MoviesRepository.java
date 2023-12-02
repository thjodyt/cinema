package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<MovieEntity, Long> {

}
