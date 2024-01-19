package com.example.monitoringservice.validator;

import com.example.monitoringservice.dto.MeasurementDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidMonthAndYearValidator implements ConstraintValidator<ValidMonthAndYear, MeasurementDto> {

    @Override
    public void initialize(ValidMonthAndYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(MeasurementDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getMonth() == null || dto.getYear() == null) {
            return false;
        }

        int currentYear = LocalDateTime.now().getYear();
        int dtoYear = dto.getYear();

        if (dtoYear < currentYear - 1 || dtoYear > currentYear) {
            return false;
        }

        int currentMonthValue = LocalDateTime.now().getMonthValue();
        int dtoMonthValue = dto.getMonth().getValue();

        if (dtoYear == currentYear && dtoMonthValue >= currentMonthValue) {
            return false;
        }

        return dtoYear == currentYear - 1 && dtoMonthValue >= 1 && dtoMonthValue <= 12;
    }
}
