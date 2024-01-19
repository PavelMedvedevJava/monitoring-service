package com.example.monitoringservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class MeasurementHistoryResponse extends BaseApiResponse{
private List<MeasurementDto> measurements;
}

