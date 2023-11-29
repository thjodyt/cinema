package com.thjodyt.cinema.controller;

import com.thjodyt.cinema.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cinema")
@RequiredArgsConstructor
public class RepresentativeController {

  @GetMapping("/admin")
  public String getAdminPanel(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
    model.addAttribute("user", principalUser.getUser());
    return "admin";
  }

}
