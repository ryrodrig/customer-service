package com.cfgtest.services.customerservice.service;

import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;
import com.cfgtest.services.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(Customer customer) {
        return null;
    }

    @Override
    public CustomerDTO getCustomer(Customer customer) {
        return customerRepository.findBySsn(customer.getSsn());
    }

    @Override
    public CustomerDTO deleteCustomer(Customer customer) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(Customer customer) {
        return null;
    }
}
