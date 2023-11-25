package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.data.ReservationDetails;
import com.thjodyt.cinema.data.SingingUser;
import com.thjodyt.cinema.data.SpectacleDTO;
import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.SpectaclesService;
import com.thjodyt.cinema.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final SpectaclesService spectaclesService;

  @GetMapping
  public String home(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {
    if (isLoggedIn(principalUser)) {
      model.addAttribute("user", principalUser.getUsername());
    }
    Collection<SpectacleDTO> currentSpectacles = spectaclesService.getCurrentSpectacles();
    model.addAttribute("spectacles", currentSpectacles);
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
    model.addAttribute("user", principalUser.getUser());
    return "user";
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
  public String reserve(@ModelAttribute ReservationDetails reservationDetails) {
    // todo proceed reservation
    System.out.println(reservationDetails.getId());
    return "redirect:/cinema/user";
  }

  private boolean isLoggedIn(PrincipalUser principalUser) {
    return !(principalUser == null);
  }

}
