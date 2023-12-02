package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.data.ChangingUser;
import com.thjodyt.cinema.data.ReservationDetails;
import com.thjodyt.cinema.data.SingingUser;
import com.thjodyt.cinema.data.SpectacleDTO;
import com.thjodyt.cinema.data.model.Spectacle;
import com.thjodyt.cinema.data.model.User;
import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.ReservationsService;
import com.thjodyt.cinema.service.SpectaclesService;
import com.thjodyt.cinema.service.UserService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final SpectaclesService spectaclesService;
  private final ReservationsService reservationsService;

  @GetMapping
  public String home(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {
    if (isLoggedIn(principalUser)) {
      model.addAttribute("user", principalUser.getUsername());
    }
    model.addAttribute("spectacles", spectaclesService.getCurrentSpectacles());
    return "home";
  }

  @GetMapping("/sign-up")
  public String signUp(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {
    if (isLoggedIn(principalUser)) {
      return "redirect:/cinema/user";
    }
    model.addAttribute("singingUser", new SingingUser());
    return "sign-up";
  }

  @PostMapping("/sign-up")
  public String signUp(@ModelAttribute SingingUser singingUser) {
    userService.createUser(singingUser);
    return "redirect:/cinema";
  }

  @GetMapping("/sign-in")
  public String signIn(@AuthenticationPrincipal PrincipalUser principalUser) {
    if (isLoggedIn(principalUser)) {
      return "redirect:/cinema/user";
    }
    return "sign-in";
  }

  @GetMapping("/user")
  public String getUser(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {
    User user = principalUser.getUser();
    model.addAttribute("user", user);
    model.addAttribute("reservations", reservationsService.getReservations(user.getId()));
    return "user";
  }

  @GetMapping("/user/change")
  public String getChangeUsersDetailsView(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUser());
    ChangingUser changingUser = new ChangingUser();
    changingUser.setName(principalUser.getUser().getName());
    changingUser.setSurname(principalUser.getUser().getSurname());
    changingUser.setEmail(principalUser.getUser().getEmail());
    model.addAttribute("changingUser", changingUser);
    return "change-user";
  }

  @PostMapping("/user/change")
  public String changeUserDetails(@ModelAttribute ChangingUser changingUser, @AuthenticationPrincipal PrincipalUser principalUser) {
    userService.changeUserDetails(changingUser, principalUser);
    return "redirect:/cinema/user";
  }

  @GetMapping("/spectacle/{id}")
  public String getSpectacle(@PathVariable long id, Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    SpectacleDTO spectacle = spectaclesService.getSpectacle(id);
    model.addAttribute("spectacle", spectacle);
    if (isLoggedIn(principalUser)) {
      model.addAttribute("user", principalUser.getUser());
    }
    ReservationDetails reservationDetails = new ReservationDetails();
    reservationDetails.setId(spectacle.getId());
    model.addAttribute("reservationDetails", reservationDetails);
    return "spectacle";
  }

  @PostMapping("/reservation")
  public String reserve(@ModelAttribute ReservationDetails reservationDetails, @AuthenticationPrincipal PrincipalUser principalUser) {
    Spectacle spectacle =  spectaclesService.findById(reservationDetails.getId());
    reservationsService.reserve(reservationDetails, principalUser.getUser(), spectacle);
    return "redirect:/cinema/user";
  }

  private boolean isLoggedIn(PrincipalUser principalUser) {
    return !(principalUser == null);
  }

}
