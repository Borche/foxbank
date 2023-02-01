package com.burch.foxbank.controller;

import com.burch.foxbank.model.Accounts;
import com.burch.foxbank.model.Customer;
import com.burch.foxbank.repository.AccountsRepository;
import com.burch.foxbank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers == null || customers.isEmpty()) {
            return null;
        }

        Accounts accounts = accountsRepository.findByCustomerId(customers.get(0).getId());
        if (accounts == null ) {
            return null;
        }

        return accounts;
    }

}
