package com.thjodyt.cinema.data.validation;

import com.thjodyt.cinema.data.dao.UsersRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UsersRepository usersRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return usersRepository.findByEmail(value).isEmpty();
  }

}
