package com.cfgtest.services.customerservice.api.controller;

import com.cfgtest.services.customerservice.api.util.CustomerApi;
import com.cfgtest.services.customerservice.model.Customer;
import com.cfgtest.services.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceController implements CustomerApi {

    private final CustomerService customerService;

//    private final CustomerMapper customerMapper;


    @Override
    public Mono<ResponseEntity<Void>> createCustomer(Mono<Customer> customer, ServerWebExchange exchange) {
        return CustomerApi.super.createCustomer(customer, exchange);
    }

    @Override
    public Mono<ResponseEntity<Void>> createCustomersWithArrayInput(Flux<Customer> customer, ServerWebExchange exchange) {
        return CustomerApi.super.createCustomersWithArrayInput(customer, exchange);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String customerName, ServerWebExchange exchange) {
        return CustomerApi.super.deleteCustomer(customerName, exchange);
    }

    @Override
    public Mono<ResponseEntity<Customer>> getCustomerByName(String customerName, ServerWebExchange exchange) {
        log.info("Get Customer by name...");
        Mono<ResponseEntity<Customer>> responseEntityMono = customerService.getCustomer(new Customer().firstName(customerName)).flatMap((customer) -> {
            log.info("Within Customer Service Controller flatMap");
            return Mono.fromSupplier(() -> ResponseEntity.ok(customer));
        });
//        Mono<ResponseEntity<Customer>> responseEntityMono = Mono.fromSupplier(() -> );
        log.info("Retrieved Customer information..");
        return responseEntityMono;
    }

    @Override
    public Mono<ResponseEntity<Void>> updateCustomer(String customerName, Mono<Customer> customer, ServerWebExchange exchange) {
        return CustomerApi.super.updateCustomer(customerName, customer, exchange);
    }
}
