package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.data.MovieDTO;
import com.thjodyt.cinema.security.PrincipalUser;
import com.thjodyt.cinema.service.MoviesService;
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

}
