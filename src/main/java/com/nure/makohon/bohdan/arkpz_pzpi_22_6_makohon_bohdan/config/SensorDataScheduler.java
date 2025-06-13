package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.config;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.SensorDataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SensorDataScheduler {

    @Autowired
    private SensorDataGenerationService sensorDataGenerationService;

    @Scheduled(fixedRate = 10000) // Execute every 5 seconds
    public void generateSensorData() {
        sensorDataGenerationService.generateDataForAllSensors();
    }
}

