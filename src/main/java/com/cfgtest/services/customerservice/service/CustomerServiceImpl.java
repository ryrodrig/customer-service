package com.cfgtest.services.customerservice.service;

import com.cfgtest.services.customerservice.api.mappers.CustomerMapper;
import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;
import com.cfgtest.services.customerservice.model.CustomerAddress;
import com.cfgtest.services.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Mono<Customer> getCustomer(Customer customer) {

        Mono<Customer> customerMono = Mono.fromSupplier(() -> {
            log.info("Within getCustomer service..");
            try {
                Thread.sleep(3000);
                log.info("Awakening Thread...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Customer()
                    .ssn(customer.getFirstName())
                    .customerType(Customer.CustomerTypeEnum.GENERAL)
                    .firstName("Test")
                    .lastName("Tester")
                    .address(new CustomerAddress());
//            return customerMapper.convertToCustomerDTO(customerLocal);
        });
        log.info("After from Supplier method...");
        return customerMono;
//        customer = customerMapper.convertToCustomer(customerService.getCustomer(customer));
//        return customerRepository.findBySsn(customer.getSsn());
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
