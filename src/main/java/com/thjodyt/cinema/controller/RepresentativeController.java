package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.data.CreatingSpectacle;
import com.thjodyt.cinema.data.Employee;
import com.thjodyt.cinema.data.MovieDTO;
import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.HallsService;
import com.thjodyt.cinema.service.MoviesService;
import com.thjodyt.cinema.service.SpectaclesService;
import com.thjodyt.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cinema/admin")
@RequiredArgsConstructor
public class RepresentativeController {

  private final MoviesService moviesService;
  private final UserService userService;
  private final HallsService hallsService;
  private final SpectaclesService spectaclesService;

  @GetMapping
  public String getAdminPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUser());
    return "admin";
  }

  @GetMapping("movies")
  public String getMoviesPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUser());
    model.addAttribute("movies", moviesService.getAllMovies());
    model.addAttribute("movie", new MovieDTO());
    return "movies";
  }

  @PostMapping("movies")
  public String addMovie(@ModelAttribute MovieDTO movieDTO) {
    moviesService.addMovie(movieDTO);
    return "redirect:/cinema/admin/movies";
  }

  @GetMapping("staff")
  public String getRepresentativesPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUser());
    model.addAttribute("staff", userService.getStaff());
    model.addAttribute("employee", new Employee());
    return "staff";
  }

  @PostMapping("staff")
  public String addEmployee(@ModelAttribute Employee employee) {
    userService.createEmployee(employee);
    return "redirect:/cinema/admin/staff";
  }

  @PostMapping("staff/{email}")
  public String deleteEmployee(@PathVariable String email, @AuthenticationPrincipal PrincipalUser principalUser) {
    if (!principalUser.getUser().getEmail().equals(email)) {
      userService.deleteEmployee(email);
    }
    return "redirect:/cinema/admin/staff";
  }

  @GetMapping("spectacles")
  public String getSpectaclesPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUser());
    model.addAttribute("movies", moviesService.getAllMovies());
    model.addAttribute("halls", hallsService.getAllHalls());
    model.addAttribute("spectacles", spectaclesService.getCurrentSpectacles());
    model.addAttribute("creatingSpectacle", new CreatingSpectacle());
    return "spectacles-panel";
  }

  @PostMapping("spectacles")
  public String setSpectacle(@ModelAttribute CreatingSpectacle creatingSpectacle) {
    // todo: validation hall mustn't be reserved
    return "redirect:/cinema/admin/spectacles";
  }

}
