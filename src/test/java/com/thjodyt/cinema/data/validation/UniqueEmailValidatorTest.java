package com.thjodyt.cinema.data.validation;

import static org.assertj.core.api.Assertions.assertThat;

import com.thjodyt.cinema.data.dao.UsersRepository;
import com.thjodyt.cinema.data.model.UserEntity;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UniqueEmailValidatorTest {

  @Mock
  private ConstraintValidatorContext context;
  @Mock
  private UsersRepository usersRepository;
  private UniqueEmailValidator uniqueEmailValidator;

  @BeforeEach
  void init() {
    uniqueEmailValidator = new UniqueEmailValidator(usersRepository);
  }

  @Test
  void shouldNotBeValidWhenEmailAlreadySignedUp() {
    // given
    UserEntity userEntity = new UserEntity();
    // when
    Mockito.when(usersRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
    // then
    assertThat(uniqueEmailValidator.isValid("janusz@mail.com", context)).isFalse();
  }

  @Test
  void shouldBeValidWhenEmailIsUnique() {
    // given
    // -- -- --
    // when
    Mockito.when(usersRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
    // then
    assertThat(uniqueEmailValidator.isValid("janusz@mail.com", context)).isTrue();

  }

}
