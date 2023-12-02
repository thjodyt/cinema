package com.thjodyt.cinema.security;

import com.thjodyt.cinema.data.model.UserEntity;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@Getter
@Setter
public class PrincipalUser implements UserDetails {

  private UserEntity userEntity;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole()));
  }

  @Override
  public String getPassword() {
    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
