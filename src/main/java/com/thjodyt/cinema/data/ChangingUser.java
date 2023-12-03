package com.thjodyt.cinema.data;

import com.thjodyt.cinema.data.validation.Password;
import com.thjodyt.cinema.data.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangingUser {

  @NotBlank(message = "Imię jest wymagane.")
  @Size(min = 3, max = 25, message = "Imię musi zawierać od 3 do 25 znaków.")
  private String name;

  @NotBlank(message = "Nazwisko jest wymagane.")
  @Size(min = 3, max = 35, message = "Pole musi zawierać od 3 do 35 znaków.")
  private String surname;

  @NotBlank(message = "E-mail jest wymagany.")
  @Email(message = "Niepoprawny adres e-mail.")
  @UniqueEmail
  private String email;

  private String oldPassword;

  @Password
  private String newPassword;

}
