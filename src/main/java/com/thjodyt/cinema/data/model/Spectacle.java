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
public class Spectacle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime date;
  private double price;
  @Column(name = "movies_id")
  private long moviesId;
  @Column(name = "halls_id")
  private long hallsId;

  @OneToMany
  @JoinColumn(name = "spectacles_id")
  private Collection<Reservation> reservations;

}
