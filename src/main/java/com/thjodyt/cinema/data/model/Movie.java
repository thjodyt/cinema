package com.thjodyt.cinema.data.model;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String description;

  @OneToMany
  @JoinColumn(name = "movies_id")
  private Collection<Spectacle> spectacles;

}
