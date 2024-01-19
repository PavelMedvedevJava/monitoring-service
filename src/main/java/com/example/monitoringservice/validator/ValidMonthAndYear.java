package com.example.monitoringservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidMonthAndYearValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMonthAndYear {
    String message() default "Invalid data for a specified period";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

