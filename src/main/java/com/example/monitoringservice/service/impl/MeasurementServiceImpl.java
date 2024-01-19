package com.example.monitoringservice.service.impl;

import com.example.monitoringservice.dto.MeasurementDto;
import com.example.monitoringservice.entity.MeasurementEntity;
import com.example.monitoringservice.entity.UserEntity;
import com.example.monitoringservice.exception.InvalidMeasurementException;
import com.example.monitoringservice.exception.UserNotFoundException;
import com.example.monitoringservice.maper.MeasurementMapper;
import com.example.monitoringservice.repository.MeasurementRepository;
import com.example.monitoringservice.repository.UserRepository;
import com.example.monitoringservice.service.MeasurementService;
import com.example.monitoringservice.validator.MeasurementValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {
    static final String USER_NOT_FOUND_MESSAGE = "User userId %d not found.";
    static final String INVALID_MEASUREMENT_MESSAGE = "New measurement is invalid for user userId= %d.";
    private static final String MESSAGE_MEASUREMENT_EXISTS = "Measurement already exists for user userId= %d, type= %s, month= %s, year= %d.";
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementServiceImpl.class);

    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;
    private final MeasurementMapper measurementMapper;
    private final MeasurementValidator measurementValidator;


    public void submitMeasurement(Long userId, @Valid MeasurementDto measurement) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            MeasurementEntity newMeasurement = measurementMapper.dtoToEntity(measurement);

            int measurementCount = measurementRepository.countByUserAndTypeAndMonthAndYear(
                    user.getUserId(),
                    newMeasurement.getType().toString(),
                    newMeasurement.getMonth().getValue(),
                    newMeasurement.getYear()
            );

            if (measurementCount > 0) {
                LOGGER.warn(String.format(MESSAGE_MEASUREMENT_EXISTS,
                        user.getUserId(), newMeasurement.getType(), newMeasurement.getMonth(), newMeasurement.getYear()));
                throw new InvalidMeasurementException(String.format(INVALID_MEASUREMENT_MESSAGE, userId));
            }

            MeasurementEntity lastMeasurement = measurementRepository.
                    findLastMeasurement(user.getUserId(), newMeasurement.getType().toString());

            if (measurementValidator.isNewMeasurementValid(user, newMeasurement, lastMeasurement)) {
                newMeasurement.setUser(user);
                measurementRepository.save(newMeasurement);
            } else {
                throw new InvalidMeasurementException(String.format(INVALID_MEASUREMENT_MESSAGE, userId));
            }
        } else {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId));
        }
    }

    public List<MeasurementDto> getMeasurementHistory(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            List<MeasurementEntity> measurements = measurementRepository.findByUser(userOptional.get());

            return measurements.stream()
                    .map(measurementMapper::entityToDto)
                    .collect(Collectors.toList());
        } else {
            LOGGER.warn(String.format(USER_NOT_FOUND_MESSAGE, userId));
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId));
        }
    }
}
