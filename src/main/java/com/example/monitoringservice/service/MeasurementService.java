package com.example.monitoringservice.service;

import com.example.monitoringservice.dto.MeasurementDto;

import java.util.List;

public interface MeasurementService {
    void submitMeasurement(Long userId, MeasurementDto measurement);

    List<MeasurementDto> getMeasurementHistory(Long userId);
}
