package com.burch.foxbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FoxBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    String providedPassword = authentication.getCredentials().toString();
    if (passwordEncoder.matches(providedPassword, userDetails.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
              username,
              providedPassword,
              userDetails.getAuthorities()
      );
    }
    throw new BadCredentialsException("Invalid credentials (FoxBank)");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
