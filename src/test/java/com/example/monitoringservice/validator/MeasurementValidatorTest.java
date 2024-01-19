package com.example.monitoringservice.validator;

import com.example.monitoringservice.dto.MeasurementType;
import com.example.monitoringservice.entity.MeasurementEntity;
import com.example.monitoringservice.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MeasurementValidatorTest {

    @Test
    public void testIsValidWhenLastMeasurementIsNull() {
        MeasurementValidator validator = new MeasurementValidator();

        UserEntity user = new UserEntity();
        MeasurementEntity newMeasurement = new MeasurementEntity();
        newMeasurement.setType(MeasurementType.GAS);

        assertTrue(validator.isNewMeasurementValid(user, newMeasurement, null));
    }

    @Test
    public void testIsValidWhenNewMeasurementIsGreater() {
        MeasurementValidator validator = new MeasurementValidator();

        UserEntity user = new UserEntity();
        MeasurementEntity newMeasurement = new MeasurementEntity();
        newMeasurement.setType(MeasurementType.GAS);
        newMeasurement.setValue(new BigDecimal("20"));

        MeasurementEntity lastMeasurement = new MeasurementEntity();
        lastMeasurement.setValue(new BigDecimal("15"));

        assertTrue(validator.isNewMeasurementValid(user, newMeasurement, lastMeasurement));
    }

    @Test
    public void testIsNotValidWhenNewMeasurementIsNotGreater() {
        MeasurementValidator validator = new MeasurementValidator();

        UserEntity user = new UserEntity();
        MeasurementEntity newMeasurement = new MeasurementEntity();
        newMeasurement.setType(MeasurementType.GAS);
        newMeasurement.setValue(new BigDecimal("15"));

        MeasurementEntity lastMeasurement = new MeasurementEntity();
        lastMeasurement.setValue(new BigDecimal("20"));

        assertFalse(validator.isNewMeasurementValid(user, newMeasurement, lastMeasurement));
    }
}