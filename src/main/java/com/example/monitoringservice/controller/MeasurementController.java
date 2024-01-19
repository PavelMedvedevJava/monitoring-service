package com.example.monitoringservice.controller;

import com.example.monitoringservice.dto.BaseApiResponse;
import com.example.monitoringservice.dto.MeasurementDto;
import com.example.monitoringservice.dto.MeasurementHistoryResponse;
import com.example.monitoringservice.service.MeasurementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/measurements")
@Validated
@RequiredArgsConstructor
@Api(tags = "Measurement API")
public class MeasurementController {
    private final MeasurementService measurementService;

    @PostMapping("/{userId}")
    @ApiOperation("Submit measurement")
    public ResponseEntity<BaseApiResponse> submitMeasurement(
            @PathVariable Long userId,
            @Validated @RequestBody MeasurementDto measurement) {
        measurementService.submitMeasurement(userId, measurement);
        BaseApiResponse response = new BaseApiResponse();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Measurement submitted successfully");
        response.setTimeStamp(new Date());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    @ApiOperation("Get measurement history")
    public ResponseEntity<MeasurementHistoryResponse> getMeasurementHistory(@PathVariable Long userId) {
        List<MeasurementDto> measurements = measurementService.getMeasurementHistory(userId);
        MeasurementHistoryResponse response = new MeasurementHistoryResponse();
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(new Date());
        response.setMeasurements(measurements);

        return ResponseEntity.ok(response);
    }
}
