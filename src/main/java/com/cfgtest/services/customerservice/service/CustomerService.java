package com.cfgtest.services.customerservice.service;

import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;
import reactor.core.publisher.Mono;


public interface CustomerService {

    CustomerDTO createCustomer(Customer customer);

    Mono<Customer> getCustomer(Customer customer);

    CustomerDTO deleteCustomer(Customer customer);

    CustomerDTO updateCustomer(Customer customer);

}
