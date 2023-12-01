package com.thjodyt.cinema.data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

  private long spectacleId;
  private LocalDateTime date;
  private double price;
  private String title;
  private int time;
  private String description;
  private String hallSymbol;
  private Collection<Integer> seats;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ReservationDTO)) {
      return false;
    }
    ReservationDTO that = (ReservationDTO) o;
    return getSpectacleId() == that.getSpectacleId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSpectacleId());
  }

}
