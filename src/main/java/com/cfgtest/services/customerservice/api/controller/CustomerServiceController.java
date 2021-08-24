package com.cfgtest.services.customerservice.api.controller;

import com.cfgtest.services.customerservice.api.exceptionhandlers.CustomerServiceException;
import com.cfgtest.services.customerservice.api.util.CustomerApi;
import com.cfgtest.services.customerservice.api.util.CustomersApi;
import com.cfgtest.services.customerservice.model.Customer;
import com.cfgtest.services.customerservice.model.ErrorDetails;
import com.cfgtest.services.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.net.URI;
import java.util.Set;
import java.util.StringJoiner;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceController implements CustomerApi, CustomersApi {

    private final CustomerService customerService;

//    private final Validator validator;

//    private boolean validate(Customer customer) {
//        Set<ConstraintViolation<Customer>> constraintViolationSet = validator.validate(customer);
//
//        if(CollectionUtils.isEmpty(constraintViolationSet)) {
//            return true;
//        }
//
//        StringJoiner stringJoiner = new StringJoiner(" ");
//        constraintViolationSet.forEach(constraintViolation -> {
//            stringJoiner.add(constraintViolation.getPropertyPath().toString()).add(":").add(constraintViolation.getMessage());
//        });
//        throw new CustomerServiceException(stringJoiner.toString(),
//                ErrorDetails.ResultEnum.FAILED,
//                "Request Validation", null, 400);
//
//
//    }

    @Override
    public Mono<ResponseEntity<Void>> createCustomer(Mono<Customer> customer,
                                                     ServerWebExchange exchange) {
        return customer
//                .filter(this::validate)
                .flatMap(customerInst -> customerService.createCustomer(customerInst))
                .flatMap((customerDTO) ->
                        Mono.fromSupplier(() ->
                                ResponseEntity.created(URI.create("/customer/" + customerDTO.getId())).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> createCustomersWithArrayInput(Flux<Customer> customer, ServerWebExchange exchange) {
        return CustomerApi.super.createCustomersWithArrayInput(customer, exchange);
    }


    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String customerId,
                                                     ServerWebExchange exchange) {
        log.info("Delete Customer by Id...");

        Mono<ResponseEntity<Void>> responseEntityMono = customerService.deleteCustomer(customerId).flatMap((customer) -> {
            log.info("Within Customer Service Controller flatMap");
            return Mono.fromSupplier(() -> ResponseEntity.status(HttpStatus.OK).build());
        });
        log.info("Deleted Customer information..");
        return responseEntityMono;
    }

    @Override
    public Mono<ResponseEntity<Customer>> getCustomerById(String customerId, ServerWebExchange exchange) {
        log.info("Get Customer by Id...");
        Mono<ResponseEntity<Customer>> responseEntityMono =
                customerService.getCustomerById(customerId).flatMap((customer) -> {
            log.info("Within Customer Service Controller flatMap");
            return Mono.fromSupplier(() -> ResponseEntity.ok(customer));
        });
        log.info("Retrieved Customer information..");
        return responseEntityMono;
    }

    @Override
    public Mono<ResponseEntity<Void>> updateCustomer(String customerId, Mono<Customer> customer, ServerWebExchange exchange) {
        log.info("Update Customer by Id...");
        Mono<ResponseEntity<Void>> responseEntityMono =
                customer.flatMap(customerObj -> customerService.updateCustomer(customerId, customerObj))
                .flatMap((updatedCustomer) -> {
                    log.info("Within Customer Service Controller flatMap");
                    return Mono.fromSupplier(() -> ResponseEntity.ok().build());
                });
        log.info("Updated Customer information..");
        return responseEntityMono;
    }


    @Override
    public Mono<ResponseEntity<Flux<Customer>>> getCustomerByFilterCriteria(String firstName, ServerWebExchange exchange) {

        log.info("Get Customer by Filter Criteria");
        if(exchange.getRequest().getQueryParams().isEmpty()){
            Mono<ResponseEntity<Flux<Customer>>> response =
                    Mono.defer(() -> Mono.fromSupplier(() -> ResponseEntity.ok(customerService.getCustomers())));
            log.info("Execution of getCustomers complete");
            return response;
        }
        log.info("Filter Criteria not implemented...");
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build());
    }


//    @GetMapping(value = "/customer-stream", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Flux<Customer> getCustomersEverySecondAsync() {
//        log.info("Streaming data");
//        return customerService.getCustomers().delaySequence(Duration.ofSeconds(2));
//    }
//
//    @GetMapping(value = "/async-flux",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//    public Flux<String> handleReqDefResult1() {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 10000   ; i++) {
//            list.add(String.valueOf(i));
//        }
//        return Flux.fromIterable(list)
//                // we have 1 sec delay to demonstrate the difference of behaviour.
//                .delayElements(Duration.ofSeconds(1));
//    }
}
