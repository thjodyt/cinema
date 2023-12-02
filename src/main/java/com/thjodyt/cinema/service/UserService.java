package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.ChangingUser;
import com.thjodyt.cinema.data.Employee;
import com.thjodyt.cinema.data.Role;
import com.thjodyt.cinema.data.SingingUser;
import com.thjodyt.cinema.data.dao.UsersRepository;
import com.thjodyt.cinema.data.model.User;
import com.thjodyt.cinema.security.PrincipalUser;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  public Collection<Employee> getStaff() {
    return usersRepository.findStaff().stream()
        .map(Mapper::mapToEmployee)
        .collect(Collectors.toList());
  }

  public void createEmployee(Employee employee) {
    String password = passwordEncoder.encode(employee.getName());
    usersRepository.save(Mapper.mapToUser(employee, password));
  }

  public void deleteEmployee(String email) {
    usersRepository.delete(usersRepository.findByEmail(email).orElseThrow());
  }

  public void changeUserDetails(ChangingUser changingUser, PrincipalUser principalUser) {
    String email = principalUser.getUser().getEmail();
    User oldUser = usersRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Could not find user: " + email));
    if (passwordEncoder.matches(changingUser.getOldPassword(), oldUser.getPassword())) {
      oldUser.setName(changingUser.getName());
      oldUser.setSurname(changingUser.getSurname());
      oldUser.setEmail(changingUser.getEmail());
      if (!(changingUser.getNewPassword().equals("") || changingUser.getNewPassword() == null)) {
        oldUser.setPassword(passwordEncoder.encode(changingUser.getNewPassword()));
      }
      usersRepository.save(oldUser);
      principalUser.setUser(oldUser);
    }
  }

  private static class Mapper {

    static User map(SingingUser singingUser, String password) {
      User user = new User();
      user.setName(singingUser.getName());
      user.setSurname(singingUser.getSurname());
      user.setEmail(singingUser.getEmail());
      user.setPassword(password);
      user.setRole(Role.ROLE_USER.name());
      return user;
    }

    static Employee mapToEmployee(User user) {
      Employee employee = new Employee();
      employee.setName(user.getName());
      employee.setSurname(user.getSurname());
      employee.setEmail(user.getEmail());
      employee.setRole(user.getRole());
      return employee;
    }

    public static User mapToUser(Employee employee, String password) {
      User user = new User();
      user.setName(employee.getName());
      user.setSurname(employee.getSurname());
      user.setEmail(employee.getEmail());
      user.setPassword(password);
      user.setRole(employee.getRole());
      return user;
    }
  }
}
