package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findByUserUserId(Integer userId);
    // Additional query methods can be added here if needed
}
