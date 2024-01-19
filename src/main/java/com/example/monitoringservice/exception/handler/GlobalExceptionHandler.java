package com.example.monitoringservice.exception.handler;

import com.example.monitoringservice.dto.BaseApiResponse;
import com.example.monitoringservice.exception.InvalidMeasurementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {InvalidMeasurementException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseApiResponse> handleInvalidMeasurementException(InvalidMeasurementException ex) {
        BaseApiResponse response = new BaseApiResponse();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(ex.getMessage());
        response.setTimeStamp(new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseApiResponse> handleOtherExceptions(Exception ex) {
        BaseApiResponse response = new BaseApiResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(ex.getMessage());
        response.setTimeStamp(new Date());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
