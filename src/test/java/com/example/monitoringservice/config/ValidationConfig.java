package com.example.monitoringservice.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
public class ValidationConfig {

    @Bean
    @Primary
    public Validator validator(ConfigurableListableBeanFactory beanFactory) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setConstraintValidatorFactory(new TestConstraintValidatorFactory(beanFactory));
        return validatorFactoryBean;
    }
}
