package com.cfgtest.services.customerservice.service;

import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;


public interface CustomerService {

    CustomerDTO createCustomer(Customer customer);

    CustomerDTO getCustomer(Customer customer);

    CustomerDTO deleteCustomer(Customer customer);

    CustomerDTO updateCustomer(Customer customer);

}
