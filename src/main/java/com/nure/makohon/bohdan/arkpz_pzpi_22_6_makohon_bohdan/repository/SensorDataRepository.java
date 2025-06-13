package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findBySensorId(Integer sensorId);
    void deleteAllBySensorId(Integer sensorId);

}
