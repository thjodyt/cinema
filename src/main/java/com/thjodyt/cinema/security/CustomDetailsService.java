package com.thjodyt.cinema.security;

import com.thjodyt.cinema.data.dao.UsersRepository;
import com.thjodyt.cinema.data.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = usersRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Could not find user: " + email));
    return new PrincipalUser(userEntity);
  }

}
