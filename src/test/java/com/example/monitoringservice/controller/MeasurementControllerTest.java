package com.example.monitoringservice.controller;

import com.example.monitoringservice.dto.MeasurementDto;
import com.example.monitoringservice.dto.MeasurementType;
import com.example.monitoringservice.service.impl.MeasurementServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeasurementServiceImpl measurementService;

    @InjectMocks
    private MeasurementController measurementController;


    @Test
    public void testSubmitMeasurement() throws Exception {
        Long userId = 1L;
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setSubmitDate(LocalDateTime.now().minusDays(1));
        measurementDto.setType(MeasurementType.GAS);
        measurementDto.setValue(BigDecimal.TEN);
        measurementDto.setMonth(Month.APRIL);
        measurementDto.setYear(2015);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/measurements/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMeasurementHistory() throws Exception {
        Long userId = 1L;
        List<MeasurementDto> measurementDtos = Arrays.asList();

        Mockito.when(measurementService.getMeasurementHistory(userId)).thenReturn(measurementDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/measurements/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}