package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.data.CreatingSpectacle;
import com.thjodyt.cinema.data.Employee;
import com.thjodyt.cinema.data.Movie;
import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.HallsService;
import com.thjodyt.cinema.service.MoviesService;
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
@RequestMapping("cinema/admin")
@RequiredArgsConstructor
public class AdminController {

  private final MoviesService moviesService;
  private final UserService userService;
  private final HallsService hallsService;
  private final SpectaclesService spectaclesService;

  @GetMapping("movies")
  public String getMoviesPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUserEntity());
    model.addAttribute("movies", moviesService.getAllMovies());
    model.addAttribute("movie", new Movie());
    return "movies";
  }

  @PostMapping("movies")
  public String addMovie(@Valid @ModelAttribute Movie movie, BindingResult result, Model model,
      @AuthenticationPrincipal PrincipalUser principalUser) {
    if (result.hasErrors()) {
      model.addAttribute("user", principalUser.getUserEntity());
      model.addAttribute("movies", moviesService.getAllMovies());
      return "movies";
    }
    moviesService.addMovie(movie);
    return "redirect:/cinema/admin/movies";
  }

  @GetMapping("staff")
  public String getStaffPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUserEntity());
    model.addAttribute("staff", userService.getStaff());
    model.addAttribute("employee", new Employee());
    return "staff";
  }

  @PostMapping("staff")
  public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result,
      Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    if (result.hasErrors()) {
      model.addAttribute("user", principalUser.getUserEntity());
      model.addAttribute("staff", userService.getStaff());
      return "staff";
    }
    userService.createEmployee(employee);
    return "redirect:/cinema/admin/staff";
  }

  @PostMapping("staff/{email}")
  public String deleteEmployee(@PathVariable String email, @AuthenticationPrincipal PrincipalUser principalUser) {
    if (!principalUser.getUserEntity().getEmail().equals(email)) {
      userService.deleteEmployee(email);
    }
    return "redirect:/cinema/admin/staff";
  }

  @GetMapping("spectacles")
  public String getSpectaclesPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUserEntity());
    model.addAttribute("movies", moviesService.getAllMovies());
    model.addAttribute("halls", hallsService.getAllHalls());
    model.addAttribute("spectacles", spectaclesService.getCurrentSpectacles());
    model.addAttribute("creatingSpectacle", new CreatingSpectacle());
    return "spectacles-panel";
  }

  @PostMapping("spectacles")
  public String setSpectacle(@Valid @ModelAttribute CreatingSpectacle creatingSpectacle,
      BindingResult bindingResult, Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("user", principalUser.getUserEntity());
      model.addAttribute("movies", moviesService.getAllMovies());
      model.addAttribute("halls", hallsService.getAllHalls());
      model.addAttribute("spectacles", spectaclesService.getCurrentSpectacles());
      return "spectacles-panel";
    }
    spectaclesService.setSpectacle(creatingSpectacle);
    return "redirect:/cinema/admin/spectacles";
  }

}
