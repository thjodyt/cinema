package com.thjodyt.cinema.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpectacleDTO {

  private Long id;
  private LocalDateTime date;
  private double price;
  private String title;
  private String description;
  private String hallSymbol;
  private Integer[][] seats;

}
