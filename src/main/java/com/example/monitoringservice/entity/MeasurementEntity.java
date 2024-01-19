package com.example.monitoringservice.entity;

import com.example.monitoringservice.dto.MeasurementType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Data
@Table(name = "measurements")
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "date")
    private LocalDateTime submitDate;

    @Enumerated(EnumType.STRING)
    private MeasurementType type;

    @Column(name = "month")
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "value")
    private BigDecimal value;
}
