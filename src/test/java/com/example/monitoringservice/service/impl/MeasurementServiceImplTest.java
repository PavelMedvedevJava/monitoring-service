package com.example.monitoringservice.service.impl;

import com.example.monitoringservice.dto.MeasurementDto;
import com.example.monitoringservice.dto.MeasurementType;
import com.example.monitoringservice.entity.MeasurementEntity;
import com.example.monitoringservice.entity.UserEntity;
import com.example.monitoringservice.exception.InvalidMeasurementException;
import com.example.monitoringservice.exception.UserNotFoundException;
import com.example.monitoringservice.maper.MeasurementMapper;
import com.example.monitoringservice.repository.MeasurementRepository;
import com.example.monitoringservice.repository.UserRepository;
import com.example.monitoringservice.validator.MeasurementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MeasurementServiceImplTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MeasurementMapper measurementMapper;

    @Mock
    private MeasurementValidator validator;

    @InjectMocks
    private MeasurementServiceImpl measurementService;
    private Long userId;
    private MeasurementDto measurementDto;
    private UserEntity userEntity;
    private MeasurementEntity measurementEntity;

    @BeforeEach
    public void init() {
        userId = 1L;
        measurementDto = new MeasurementDto();
        userEntity = new UserEntity();
        userEntity.setUserId(userId);
        measurementEntity = new MeasurementEntity();

        measurementEntity.setUser(userEntity);
        measurementEntity.setSubmitDate(LocalDateTime.now().minusDays(1));
        measurementEntity.setType(MeasurementType.GAS);
        measurementEntity.setValue(BigDecimal.TEN);
        measurementEntity.setMonth(Month.APRIL);
        measurementEntity.setYear(2015);
    }

    @Test
    public void testSubmitMeasurement_UserNotFound() {
        Long userId = 1L;
        MeasurementDto measurementDto = new MeasurementDto();

        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> measurementService.submitMeasurement(userId, measurementDto));

        verify(userRepository, times(1)).findByUserId(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSubmitMeasurement_InvalidMeasurement() {
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(userEntity));
        when(measurementMapper.dtoToEntity(measurementDto)).thenReturn(measurementEntity);
        when(measurementRepository.countByUserAndTypeAndMonthAndYear(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(1);
        when(measurementRepository.findLastMeasurement(anyLong(), anyString())).thenReturn(measurementEntity);
        when(validator.isNewMeasurementValid(any(), any(), any())).thenReturn(false);

        assertThrows(InvalidMeasurementException.class, () -> measurementService.submitMeasurement(userId, measurementDto));

        verify(userRepository, times(1)).findByUserId(userId);
        verify(measurementMapper, times(1)).dtoToEntity(measurementDto);
        verify(measurementRepository, times(1)).countByUserAndTypeAndMonthAndYear(anyLong(), anyString(), anyInt(), anyInt());
        verifyNoMoreInteractions(userRepository, measurementMapper, measurementRepository);
    }

    @Test
    public void testSubmitMeasurement_ValidMeasurement() {
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(userEntity));
        when(measurementMapper.dtoToEntity(measurementDto)).thenReturn(measurementEntity);
        when(measurementRepository.countByUserAndTypeAndMonthAndYear(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(0);
        when(measurementRepository.findLastMeasurement(anyLong(), anyString())).thenReturn(null);
        when(validator.isNewMeasurementValid(any(), any(), any())).thenReturn(true);

        measurementService.submitMeasurement(userId, measurementDto);

        verify(userRepository, times(1)).findByUserId(userId);
        verify(measurementMapper, times(1)).dtoToEntity(measurementDto);
        verify(measurementRepository, times(1)).countByUserAndTypeAndMonthAndYear(anyLong(), anyString(), anyInt(), anyInt());
        verify(measurementRepository, times(1)).findLastMeasurement(anyLong(), anyString());
        verify(measurementRepository, times(1)).save(any(MeasurementEntity.class));
        verifyNoMoreInteractions(userRepository, measurementMapper, measurementRepository);
    }
}