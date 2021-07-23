package com.cfgtest.services.customerservice.api.controller;

import com.cfgtest.services.customerservice.api.CustomerApi;
import com.cfgtest.services.customerservice.api.mappers.CustomerMapper;
import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;
import com.cfgtest.services.customerservice.model.CustomerAddress;
import com.cfgtest.services.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerServiceController implements CustomerApi {

    private final CustomerService customerService;

//    private final CustomerMapper customerMapper;

    @Override
    public ResponseEntity<Void> createCustomer(Customer customer) {
        CustomerDTO customerDTO = customerService.createCustomer(customer);
        return ResponseEntity.created(URI.create("/customer/" + customerDTO.getId())).build();
    }

    @Override
    public ResponseEntity<Void> createCustomersWithArrayInput(List<Customer> customer) {
        return CustomerApi.super.createCustomersWithArrayInput(customer);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(String customerName) {
        return CustomerApi.super.deleteCustomer(customerName);
    }

    @Override
    public ResponseEntity<Customer> getCustomerByName(String customerName) {
        Customer customer =
                new Customer()
                        .ssn(customerName)
                        .customerType(Customer.CustomerTypeEnum.GENERAL)
                        .firstName("Test")
                        .lastName("Tester")
                        .address(new CustomerAddress());
//        customer = customerMapper.convertToCustomer(customerService.getCustomer(customer));
        return ResponseEntity.ok(customer);
    }

    @Override
    public ResponseEntity<Void> updateCustomer(String customerName, Customer customer) {
        return CustomerApi.super.updateCustomer(customerName, customer);
    }
}
