package com.example.monitoringservice.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.ConstraintValidator;

public class TestConstraintValidatorFactory extends SpringConstraintValidatorFactory {

    private final ConfigurableListableBeanFactory beanFactory;

    public TestConstraintValidatorFactory(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        if (key.getName().equals("com.example.monitoringservice.validator.ValidMonthAndYearValidator")) {
            return (T) new NoValidationValidator();
        }
        return super.getInstance(key);
    }
}
