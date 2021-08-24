package com.cfgtest.services.customerservice.service;

import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerService {

    Mono<CustomerDTO> createCustomer(Customer customer);

    Mono<Customer> getCustomerById(String customerId);

    Flux<Customer> getCustomers();

    Mono<Customer> getCustomer(Customer customer);

    CustomerDTO deleteCustomer(Customer customer);

    Mono<CustomerDTO> deleteCustomer(String customerId);

    Mono<CustomerDTO> updateCustomer(String customerId, Customer customer);

}
