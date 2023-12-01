package com.thjodyt.cinema.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatingSpectacle {

  private LocalDateTime date;
  private double price;
  private long movieId;
  private long hallId;

}
