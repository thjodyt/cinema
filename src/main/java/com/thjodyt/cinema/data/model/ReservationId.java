package com.thjodyt.cinema.data.model;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ReservationId implements Serializable {

  private Spectacle spectacle;
  private int seatNum;

}
