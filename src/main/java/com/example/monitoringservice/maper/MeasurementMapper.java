package com.example.monitoringservice.maper;

import com.example.monitoringservice.dto.MeasurementDto;
import com.example.monitoringservice.entity.MeasurementEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeasurementMapper {
    private final ModelMapper modelMapper;
    public MeasurementDto entityToDto(MeasurementEntity measurementEntity) {
        return modelMapper.map(measurementEntity, MeasurementDto.class);
    }
    public MeasurementEntity dtoToEntity(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, MeasurementEntity.class);
    }
}
