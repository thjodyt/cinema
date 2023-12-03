package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.data.ChangingUser;
import com.thjodyt.cinema.data.ReservationDetails;
import com.thjodyt.cinema.data.SingingUser;
import com.thjodyt.cinema.data.Spectacle;
import com.thjodyt.cinema.data.model.SpectacleEntity;
import com.thjodyt.cinema.data.model.UserEntity;
import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.ReservationsService;
import com.thjodyt.cinema.service.SpectaclesService;
import com.thjodyt.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final SpectaclesService spectaclesService;
  private final ReservationsService reservationsService;

  @GetMapping
  public String getHomePage(@AuthenticationPrincipal PrincipalUser user, Model model) {
    if (isLoggedIn(user)) {
      model.addAttribute("user", user.getUserEntity());
    }
    model.addAttribute("spectacles", spectaclesService.getCurrentSpectacles());
    return "home";
  }

  @GetMapping("/sign-up")
  public String getSignUpPage(@AuthenticationPrincipal PrincipalUser user, Model model) {
    if (isLoggedIn(user)) {
      return "redirect:/cinema/user";
    }
    model.addAttribute("singingUser", new SingingUser());
    return "sign-up";
  }

  @PostMapping("/sign-up")
  public String signUp(@Valid @ModelAttribute SingingUser singingUser, BindingResult result) {
    if (result.hasErrors()) {
      return "sign-up";
    }
    userService.createUser(singingUser);
    return "redirect:/sign-in";
  }

  @GetMapping("/sign-in")
  public String getSignInPage(@AuthenticationPrincipal PrincipalUser user) {
    if (isLoggedIn(user)) {
      return "redirect:/cinema/user";
    }
    return "sign-in";
  }

  @GetMapping("/user")
  public String getUserPage(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {
    UserEntity userEntity = principalUser.getUserEntity();
    model.addAttribute("user", userEntity);
    model.addAttribute("reservations", reservationsService.getReservations(userEntity.getId()));
    return "user";
  }

  @GetMapping("/user/change")
  public String getChangeUsersPropertiesPage(Model model, @AuthenticationPrincipal PrincipalUser user) {
    model.addAttribute("user", user.getUserEntity());
    model.addAttribute("changingUser", createChangingUser(user));
    return "change-user";
  }

  @PostMapping("/user/change")
  public String changeUserProperties(@Valid @ModelAttribute ChangingUser changingUser,
      BindingResult result, Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    if (result.hasErrors()) {
      model.addAttribute("user", principalUser.getUserEntity());
      return "change-user";
    }
    userService.changeUserDetails(changingUser, principalUser);
    return "redirect:/cinema/user";
  }

  @GetMapping("/spectacle/{id}")
  public String getSpectaclesReservationPage(@PathVariable long id, Model model, @AuthenticationPrincipal PrincipalUser user) {
    Spectacle spectacle = spectaclesService.getSpectacle(id);
    model.addAttribute("spectacle", spectacle);
    if (isLoggedIn(user)) {
      model.addAttribute("user", user.getUserEntity());
    }
    ReservationDetails reservationDetails = new ReservationDetails();
    reservationDetails.setId(spectacle.getId());
    model.addAttribute("reservationDetails", reservationDetails);
    return "spectacle";
  }

  @PostMapping("/reservation")
  public String reserve(@ModelAttribute ReservationDetails reservationDetails, @AuthenticationPrincipal PrincipalUser user) {
    SpectacleEntity spectacleEntity =  spectaclesService.findById(reservationDetails.getId());
    reservationsService.reserve(reservationDetails, user.getUserEntity(), spectacleEntity);
    return "redirect:/cinema/user";
  }

  private boolean isLoggedIn(PrincipalUser principalUser) {
    return !(principalUser == null);
  }

  private ChangingUser createChangingUser(PrincipalUser principalUser) {
    ChangingUser changingUser = new ChangingUser();
    changingUser.setName(principalUser.getUserEntity().getName());
    changingUser.setSurname(principalUser.getUserEntity().getSurname());
    changingUser.setEmail(principalUser.getUserEntity().getEmail());
    return changingUser;
  }

}
