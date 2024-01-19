package com.example.monitoringservice.dto;

import com.example.monitoringservice.validator.ValidMonthAndYear;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@Data
@ValidMonthAndYear
@EqualsAndHashCode
public class MeasurementDto {

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date should be in the past or present")
    private LocalDateTime submitDate;

    @NotNull(message = "Type cannot be null")
    private MeasurementType type;

    @Positive(message = "Value should be a positive number")
    private BigDecimal value;

    private Month month;

    private Integer year;
}
