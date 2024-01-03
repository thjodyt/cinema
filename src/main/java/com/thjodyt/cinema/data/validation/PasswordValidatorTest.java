package com.thjodyt.cinema.data.validation;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

  private final PasswordValidator passwordValidator = new PasswordValidator();
  @Mock
  private ConstraintValidatorContext context;

  @ParameterizedTest(name = "{index} - {arguments} - should be valid.")
  @ValueSource(strings = {"Pass123q", "HaRdp@55", "dw7@@oLL", "ylQ8*tHt3", ""})
  void shouldBeValid(String password) {
    assertThat(passwordValidator.isValid(password, context)).isTrue();
  }

  @ParameterizedTest(name = "{index} - {arguments} - shouldn't be valid.")
  @ValueSource(strings = {"withoutDigit", "without_big_letter_123", "WITHOUT_SMALL_LETTER_123", "sH0rt"})
  void shouldNotBeValid(String password) {
    assertThat(passwordValidator.isValid(password, context)).isFalse();
  }

}
