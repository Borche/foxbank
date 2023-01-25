package com.burch.foxbank.controller;

import com.burch.foxbank.model.Customer;
import com.burch.foxbank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
