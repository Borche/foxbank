package com.burch.foxbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class FoxbankApplication {

  public static void main(String[] args) {
    SpringApplication.run(FoxbankApplication.class, args);
  }

}
