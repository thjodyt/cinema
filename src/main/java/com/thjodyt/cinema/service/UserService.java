package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.ChangingUser;
import com.thjodyt.cinema.data.Employee;
import com.thjodyt.cinema.data.Role;
import com.thjodyt.cinema.data.SingingUser;
import com.thjodyt.cinema.data.dao.UsersRepository;
import com.thjodyt.cinema.data.model.UserEntity;
import com.thjodyt.cinema.security.PrincipalUser;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  public void createUser(SingingUser singingUser) {
    String password = passwordEncoder.encode(singingUser.getPassword());
    UserEntity userEntity = Mapper.map(singingUser, password);
    usersRepository.save(userEntity);
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
    String email = principalUser.getUserEntity().getEmail();
    UserEntity oldUserEntity = usersRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Could not find user: " + email));
    if (passwordEncoder.matches(changingUser.getOldPassword(), oldUserEntity.getPassword())) {
      oldUserEntity.setName(HtmlUtils.htmlEscape(changingUser.getName()));
      oldUserEntity.setSurname(HtmlUtils.htmlEscape(changingUser.getSurname()));
      oldUserEntity.setEmail(changingUser.getEmail());
      if (!(changingUser.getNewPassword().equals("") || changingUser.getNewPassword() == null)) {
        oldUserEntity.setPassword(passwordEncoder.encode(changingUser.getNewPassword()));
      }
      usersRepository.save(oldUserEntity);
      principalUser.setUserEntity(oldUserEntity);
    }
  }

  private static class Mapper {

    static UserEntity map(SingingUser singingUser, String password) {
      UserEntity userEntity = new UserEntity();
      userEntity.setName(HtmlUtils.htmlEscape(singingUser.getName()));
      userEntity.setSurname(HtmlUtils.htmlEscape(singingUser.getSurname()));
      userEntity.setEmail(singingUser.getEmail());
      userEntity.setPassword(password);
      userEntity.setRole(Role.ROLE_USER.name());
      return userEntity;
    }

    static Employee mapToEmployee(UserEntity userEntity) {
      Employee employee = new Employee();
      employee.setName(userEntity.getName());
      employee.setSurname(userEntity.getSurname());
      employee.setEmail(userEntity.getEmail());
      employee.setRole(userEntity.getRole());
      return employee;
    }

    public static UserEntity mapToUser(Employee employee, String password) {
      UserEntity userEntity = new UserEntity();
      userEntity.setName(HtmlUtils.htmlEscape(employee.getName()));
      userEntity.setSurname(HtmlUtils.htmlEscape(employee.getSurname()));
      userEntity.setEmail(employee.getEmail());
      userEntity.setPassword(password);
      userEntity.setRole(employee.getRole());
      return userEntity;
    }
  }
}
