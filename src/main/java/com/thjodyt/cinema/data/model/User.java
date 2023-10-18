package com.thjodyt.cinema.data.model;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String role;
  private boolean isActive;
  private String activationCode;

  @OneToMany
  @JoinColumn(name = "users_id")
  private Collection<Reservation> reservations;

}
