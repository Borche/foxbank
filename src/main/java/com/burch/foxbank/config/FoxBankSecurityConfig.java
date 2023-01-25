package com.burch.foxbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class FoxBankSecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
            .requestMatchers("/contact", "/notices", "/register").permitAll()
            .and().formLogin()
            .and().httpBasic();
    return http.build();
  }

  /*@Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }*/

  /*@Bean
  public InMemoryUserDetailsManager userDetailsManager() {
    // Approach 1
    *//*
    UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("12345")
            .authorities("admin")
            .build();
    UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("12345")
            .authorities("read")
            .build();

    return new InMemoryUserDetailsManager(admin, user);
    *//*

    // Approach 2
    UserDetails admin = User.withUsername("admin")
            .password("12345")
            .authorities("admin")
            .build();
    UserDetails user = User.withUsername("user")
            .password("12345")
            .authorities("read")
            .build();
    return new InMemoryUserDetailsManager(admin, user);
  }*/

  /**
   * NoOpPasswordEncoder is not intended for prod.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    // For approach 2 above
    return NoOpPasswordEncoder.getInstance();
  }
}
