package com.example.monitoringservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseApiResponse {
    private HttpStatus status;
    private String message;
    private Date timeStamp;
}
