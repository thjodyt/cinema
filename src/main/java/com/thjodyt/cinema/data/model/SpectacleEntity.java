package com.thjodyt.cinema.data.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "spectacles")
@Getter
@Setter
public class SpectacleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime date;
  private double price;
  private LocalDateTime timeStart;
  private LocalDateTime timeEnd;

  @ManyToOne
  @JoinColumn(name = "movies_id")
  private MovieEntity movieEntity;

  @ManyToOne
  @JoinColumn(name = "halls_id")
  private HallEntity hallEntity;

  @OneToMany
  @JoinColumn(name = "spectacles_id")
  private Collection<ReservationEntity> reservationEntities;

}
