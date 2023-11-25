package com.thjodyt.cinema.data;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDetails {

  private Long id;
  private List<Integer> seats;

}
