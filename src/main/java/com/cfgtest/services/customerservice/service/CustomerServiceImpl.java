package com.cfgtest.services.customerservice.service;

import com.cfgtest.services.customerservice.api.exceptionhandlers.CustomerServiceException;
import com.cfgtest.services.customerservice.api.mappers.CustomerMapper;
import com.cfgtest.services.customerservice.dto.CustomerDTO;
import com.cfgtest.services.customerservice.model.Customer;
import com.cfgtest.services.customerservice.model.CustomerAddress;
import com.cfgtest.services.customerservice.model.ErrorDetails;
import com.cfgtest.services.customerservice.repository.CustomerRepository;
import com.cfgtest.services.customerservice.utils.CustomerDTOValidator;
import com.cfgtest.services.customerservice.utils.OnCreate;
import com.cfgtest.services.customerservice.utils.OnUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final CustomerDTOValidator customerDTOValidator;

    @Override
    @Transactional
    // Activates OnCreate group when performing validation.
    public Mono<CustomerDTO> createCustomer(Customer customer) {
        return Mono.fromSupplier(() -> {
            log.info("Create Customer");
            CustomerDTO customerDTO = customerMapper.convertToCustomerDTO(customer);
            // needs to be a separate class.. believe its because proxys are created around beans
            // and not internal calls.
            customerDTOValidator.validateOnCreate(customerDTO);
            log.info("CustomerDTO to persist: {}", customerDTO);
            return customerRepository.save(customerDTO);
        }).onErrorMap(throwable -> throwServiceException("CREATE CUSTOMER", 500).apply(throwable));
    }


    @Override
    public Mono<Customer> getCustomerById(String customerId) {

        // Implementation with Mono.just -  once you have a value you can wrap it into a Mono and subscribers down the line will get it.
//        Mono<Customer> customerMono =
//                Mono.just(customerId).flatMap(customerIdMono -> {
//                    log.info("Reading data from Repository *****");
//                    try {
//                        Thread.sleep(3000);
//                        log.info("Awakening Thread...");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Mono<Optional<CustomerDTO>> customerDataMono = Mono.just(customerRepository.findById(UUID.fromString(customerIdMono)));
//                    log.info("Read data from Repository *****");
//                    return customerDataMono;
//                }).flatMap(customerDTO -> {
//            log.info("Evaluating database record *****");
//            customerDTO.orElseThrow(() -> new CustomerServiceException("Customer Data Not Found."
//                    , ErrorDetails.ResultEnum.FAILED, "NOT_FOUND", null, 404));
//            return Mono.just(customerMapper.convertToCustomer(customerDTO.get()));
//        }).onErrorMap(throwable -> {
//            throwable.printStackTrace();
//            String msg = throwable.getMessage();
//            throw new CustomerServiceException(msg, throwable, ErrorDetails.ResultEnum.FAILED,
//                    "DB", null, 500);
//        });
//        log.info("Exiting getCustomerById ******");
//        return customerMono;


        // Implementation with Mono.defer --> lets you provide the whole expression that supplies
        // the resulting Mono instance. The evaluation of this expression is deferred until somebody
        // subscribes. Inside of this expression you can additionally use control structures like
        // Mono.error(throwable) to signal an error condition (you cannot do this with Mono.just).

        return Mono.defer(() -> {
            log.info("Reading data from Repository from within defer *****");
            return Mono.just(customerRepository.findById(UUID.fromString(customerId)));
        }).flatMap(customerDTO -> {
            log.info("Evaluating database record *****");
            customerDTO.orElseThrow(() -> new CustomerServiceException("Customer Data Not Found."
                    , ErrorDetails.ResultEnum.FAILED, "NOT_FOUND", null, 404));
            return Mono.just(customerMapper.convertToCustomer(customerDTO.get()));
        }).onErrorMap(throwable -> throwServiceException("DB", 500).apply(throwable));

    }

    private Function<Throwable, CustomerServiceException> throwServiceException(String sourceSystem, Integer failCode) {
        return throwable -> {
            if(throwable instanceof CustomerServiceException) {
                throw (CustomerServiceException) throwable;
            }
            if(ExceptionUtils.getRootCause(throwable) instanceof ConstraintViolationException) {
                throw (ConstraintViolationException) ExceptionUtils.getRootCause(throwable);
            }
            throwable.printStackTrace();
            String msg = throwable.getMessage();
            throw new CustomerServiceException(msg, throwable, ErrorDetails.ResultEnum.FAILED,
                    sourceSystem, null, failCode);
        };
    }

    @Override
    public Flux<Customer> getCustomers() {
        return Flux.defer(() -> Flux.fromIterable(customerRepository.findAll()))
                .switchIfEmpty(Flux.defer(() -> Flux.just(new CustomerDTO())))
                .flatMap(customerDTO -> Flux.just(customerMapper.convertToCustomer(customerDTO)))
                .onErrorMap(throwable -> throwServiceException(
                        "DB",
                        500).apply(throwable));
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
    }

    @Override
    public CustomerDTO deleteCustomer(Customer customer) {
        return null;
    }

    @Override
    public Mono<CustomerDTO> deleteCustomer(String customerId) {
        return Mono.defer(() -> {
            log.info("Deleting data from Repository from within defer *****");
            customerRepository.deleteById(UUID.fromString(customerId));
            return Mono.just(new CustomerDTO());
        }).onErrorMap(throwable -> throwServiceException("DB", 500).apply(throwable));
    }

    @Override
    @Transactional
    // Activates OnUpdate (custom interface) group when performing validation.
    @Validated(OnUpdate.class)
    public Mono<CustomerDTO> updateCustomer(String customerId, Customer customer) {
       return Mono.defer(() -> {
            log.info("Updating customer data from Repository from within defer *****");
            return Mono.just(customerRepository.findById(UUID.fromString(customerId)))
                    .switchIfEmpty(Mono.error(new CustomerServiceException("Invalid Customer Id when updating.", ErrorDetails.ResultEnum.FAILED,"DB",null,400)))
                    .flatMap(customerFromDB -> {
                        log.info("Customer found in DB to update..");
                        CustomerDTO customerToUpdate = customerFromDB.get();
                        // Custom group validator.
                        customerDTOValidator.validateOnUpdate(customerToUpdate);
                        customerMapper.updateCustomerDTO(customer, customerToUpdate);
                        CustomerDTO updatedCustomerDTO = customerRepository.save(customerToUpdate);
                        return Mono.just(updatedCustomerDTO);
                    });
        }).onErrorMap(throwable -> throwServiceException(
                "DB", 500).apply(throwable));
    }
}
