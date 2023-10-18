package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
