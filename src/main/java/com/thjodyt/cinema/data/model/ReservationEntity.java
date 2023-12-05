package com.thjodyt.cinema.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@IdClass(ReservationId.class)
@Getter
@Setter
public class ReservationEntity {

  @Id
  @ManyToOne
  @JoinColumn(name = "spectacles_id")
  private SpectacleEntity spectacleEntity;
  @Id
  @Column(name = "seat_num")
  private int seatNum;
  @Column(name = "users_id")
  private Long usersId;

}
