package com.cfgtest.services.customerservice.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


// Constraint Validator
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CitizensEmployerValidator.class)
@Documented
public  @interface CFGCheck {

    String message() default "{Citizens.Employee}";

    Class<?> [] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
