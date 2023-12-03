package com.thjodyt.cinema.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        req -> req
            .requestMatchers(
                "/cinema",
                "/cinema/sign-up",
                "/cinema/sign-in",
                "/cinema/spectacle/**").permitAll()
            .requestMatchers(
                "/cinema/admin/**"
            ).hasRole("ADMIN")
            .requestMatchers(
                "/cinema/user/**"
            ).authenticated()
            .anyRequest().hasAnyRole("USER", "CASHIER")
    ).formLogin(
        form -> form
            .loginPage("/cinema/sign-in")
            .defaultSuccessUrl("/cinema/user")
    ).logout(
        logout -> logout
            .logoutUrl("/cinema/logout").permitAll()
            .logoutSuccessUrl("/cinema")
    );
    return http.build();
  }

}
