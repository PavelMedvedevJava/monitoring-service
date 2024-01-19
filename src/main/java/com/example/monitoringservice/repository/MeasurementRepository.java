package com.example.monitoringservice.repository;

import com.example.monitoringservice.entity.MeasurementEntity;
import com.example.monitoringservice.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends CrudRepository<MeasurementEntity, Long> {
    List<MeasurementEntity> findByUser(UserEntity user);

    @Query(value = "SELECT * FROM v_last_measurements m WHERE" +
            " m.user_id = :userId" +
            " AND m.type = :type ORDER BY m.date DESC LIMIT 1", nativeQuery = true)
    MeasurementEntity findLastMeasurement(@Param("userId") Long userId, @Param("type") String type);

    @Query(value = "SELECT COUNT(*) FROM v_last_measurements m WHERE" +
            " m.user_id = :userId" +
            " AND m.type = :type" +
            " AND m.month = :month" +
            " AND m.year = :year", nativeQuery = true)
    int countByUserAndTypeAndMonthAndYear(
            @Param("userId") Long userId,
            @Param("type") String type,
            @Param("month") int month,
            @Param("year") int year
    );
}
