package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.Hall;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallsRepository extends JpaRepository<Hall, Long> {

  Optional<Hall> findBySymbol(String hallA);

}
