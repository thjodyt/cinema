package com.thjodyt.cinema.data;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatingSpectacle {

  @Future(message = "Data musi być przyszła.")
  private LocalDateTime date;

  @Min(value = 0, message = "Cena nusi być dodatnia.")
  private Double price;

  @NotNull(message = "Film jest wymagany.")
  private Long movieId;

  @NotNull(message = "Hala jest wymagana.")
  private Long hallId;

}
