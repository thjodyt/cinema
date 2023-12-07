package com.thjodyt.cinema.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {

  private long id;

  @NotBlank(message = "Tytuł jest wymagany.")
  @Size(min = 3, max = 45, message = "Tytuł musi zawierać od 3 do 25 znaków.")
  private String title;
  @Min(value = 30, message = "Minimalny czas to 30 minut.")
  private Integer time;
  @NotBlank(message = "Opis jest wymagany.")
  @Size(min = 3, max = 25, message = "Opis musi zawierać od 3 do 255 znaków.")
  private String description;

}
