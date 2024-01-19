package com.example.monitoringservice.exception;

public class InvalidMeasurementException extends RuntimeException {
    public InvalidMeasurementException(String message) {
        super(message);
    }
}
