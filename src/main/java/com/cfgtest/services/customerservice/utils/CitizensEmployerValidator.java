package com.cfgtest.services.customerservice.utils;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
// Constraint validator implementation.
public class CitizensEmployerValidator implements ConstraintValidator<CFGCheck, String> {


    @Override
    public boolean isValid(String employerName,
                           ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.containsAnyIgnoreCase(employerName,"CFG","Citizens")) {
            return false;
        }

        return true;
    }
}
