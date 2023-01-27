package com.burch.foxbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/* These annotations are not needed because the repository
and model is in the same package or a subpackage in this package. */
// @EnableJpaRepositories("com.burch.foxbank.repository")
// @EntityScan("com.burch.foxbank.model")
// @EnableWebSecurity(debug = true)
@SpringBootApplication
public class FoxbankApplication {

  public static void main(String[] args) {
    SpringApplication.run(FoxbankApplication.class, args);
  }

}
