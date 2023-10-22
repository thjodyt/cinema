package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.Role;
import com.thjodyt.cinema.data.SingingUser;
import com.thjodyt.cinema.data.dao.UsersRepository;
import com.thjodyt.cinema.data.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  public void createUser(SingingUser singingUser) {
    String password = passwordEncoder.encode(singingUser.getPassword());
    User user = Mapper.map(singingUser, password);
    usersRepository.save(user);
  }

  static class Mapper {

    static User map(SingingUser singingUser, String password) {
      User user = new User();
      user.setName(singingUser.getName());
      user.setSurname(singingUser.getSurname());
      user.setEmail(singingUser.getEmail());
      user.setPassword(password);
      user.setRole(Role.ROLE_USER.name());
      return user;
    }
  }
}
