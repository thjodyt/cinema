package com.thjodyt.cinema.data.dao;

import com.thjodyt.cinema.data.model.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  @Query("select u from User u where u.role = 'ROLE_ADMIN' or u.role = 'ROLE_CASH'")
  Collection<User> findStaff();

}
