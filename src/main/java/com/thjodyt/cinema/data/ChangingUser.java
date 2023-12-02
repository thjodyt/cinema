package com.thjodyt.cinema.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangingUser {

  private String name;
  private String surname;
  private String email;
  private String oldPassword;
  private String newPassword;

}
