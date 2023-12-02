package com.thjodyt.cinema.data.model;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "halls")
@Getter
@Setter
public class HallEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String symbol;
  @Column(name = "r")
  private int rows;
  @Column(name = "c")
  private int cols;

  @OneToMany
  @JoinColumn(name = "halls_id")
  private Collection<SpectacleEntity> spectacleEntities;

}
