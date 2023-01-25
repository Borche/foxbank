package com.burch.foxbank.config;

import com.burch.foxbank.model.Customer;
import com.burch.foxbank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoxBankUserDetailsService implements UserDetailsService {

  @Autowired
  CustomerRepository customerRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String userName, password = null;
    List<GrantedAuthority> authorities = null;
    List<Customer> customers = customerRepository.findByEmail(username);
    if (customers.size() == 0) {
      throw new UsernameNotFoundException("User not found: " + username);
    } else if (customers.size() >= 2) {
      throw new IllegalStateException("Multiple users found in database: " + username);
    } else {
      // All good
      userName = customers.get(0).getEmail();
      password = customers.get(0).getPwd();
      authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
    }
    return new User(userName, password, authorities);
  }
}
