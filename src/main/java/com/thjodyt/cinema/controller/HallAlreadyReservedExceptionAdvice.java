package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.exception.HallAlreadyReservedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HallAlreadyReservedExceptionAdvice {

  @ExceptionHandler(HallAlreadyReservedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public String hallAlreadyReservedExceptionHandler(HallAlreadyReservedException e, Model model,
      @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("exception", e.getMessage());
    model.addAttribute("user", principalUser.getUserEntity());
    return "hall-already-reserved";
  }

}
