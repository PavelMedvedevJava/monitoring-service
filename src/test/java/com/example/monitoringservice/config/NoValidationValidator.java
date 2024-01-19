package com.example.monitoringservice.config;

import com.example.monitoringservice.validator.ValidMonthAndYear;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NoValidationValidator implements ConstraintValidator<ValidMonthAndYear, Object> {

    @Override
    public void initialize(ValidMonthAndYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return true;
    }
}
