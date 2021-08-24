package com.cfgtest.services.customerservice.utils;

import com.cfgtest.services.customerservice.dto.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

// Separate class to validate custom group.
@Service
@Validated
public class CustomerDTOValidator {

    @Validated(OnCreate.class)
    public void validateOnCreate(@Valid CustomerDTO customerDTO) {}


    @Validated(OnUpdate.class)
    public void validateOnUpdate(@Valid CustomerDTO customerDTO) {}
}
