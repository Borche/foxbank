package com.burch.foxbank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

  @GetMapping("/myLoans")
  public String getLoans() {
    return "loans";
  }
}
