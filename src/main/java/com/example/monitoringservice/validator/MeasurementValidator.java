package com.example.monitoringservice.validator;

import com.example.monitoringservice.entity.MeasurementEntity;
import com.example.monitoringservice.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class MeasurementValidator {
    private static final String MESSAGE_NO_PREVIOUS_MEASUREMENT = "No previous measurement found for user userId= %d, type= %s.";
    private static final String MESSAGE_INVALID_MEASUREMENT = "New measurement value= %s is not greater than the last value= %s for user userId= %d, type %s.";


    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementValidator.class);

    public boolean isNewMeasurementValid(UserEntity user, MeasurementEntity newMeasurement, MeasurementEntity lastMeasurement) {

        if (lastMeasurement == null ) {
            LOGGER.debug(String.format(MESSAGE_NO_PREVIOUS_MEASUREMENT, user.getUserId(), newMeasurement.getType().toString()));
            return true;
        }

        BigDecimal lastValue = lastMeasurement.getValue();
        BigDecimal newValue = newMeasurement.getValue();

        boolean isValid = newValue.compareTo(lastValue) > 0;

        if (!isValid) {
            LOGGER.warn(String.format(MESSAGE_INVALID_MEASUREMENT,
                    newValue, lastValue, user.getUserId(), newMeasurement.getType().toString()));
        }

        return isValid;
    }
}
