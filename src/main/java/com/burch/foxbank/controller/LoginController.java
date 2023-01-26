package com.burch.foxbank.controller;

import com.burch.foxbank.model.Customer;
import com.burch.foxbank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LoginController {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
    Customer savedCustomer = null;
    ResponseEntity<String> response = null;
    try {
      String hashedPassword = passwordEncoder.encode(customer.getPwd());
      customer.setPwd(hashedPassword);
      // customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
      savedCustomer = customerRepository.save(customer);
      if (savedCustomer.getId() > 0) {
        response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Customer created successfully.");
      }
    } catch (Exception ex) {
      response = ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(ex.getMessage());
    }
    return response;
  }

  @RequestMapping("/user")
  public Customer getUserDetailsAfterLogin(Authentication authentication) {
    List<Customer> customers = customerRepository.findByEmail(authentication.getName());
    if (customers.size() > 0) {
      return customers.get(0);
    } else {
      return null;
    }

  }
}
