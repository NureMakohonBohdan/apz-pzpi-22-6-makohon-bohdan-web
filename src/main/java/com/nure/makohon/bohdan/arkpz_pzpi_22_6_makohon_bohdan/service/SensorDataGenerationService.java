package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Sensor;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SensorData;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorDataRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class SensorDataGenerationService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    private final Random random = new Random();

    public void generateDataForAllSensors() {
        List<Sensor> sensors = sensorRepository.findAll();
        for (Sensor sensor : sensors) {
            SensorData sensorData = new SensorData();
            sensorData.setSensor(sensor);
            sensorData.setValue(generateRandomValue(sensor.getSensorType()));
            sensorData.setTimestamp(LocalDateTime.now());
            sensorDataRepository.save(sensorData);
        }
    }

    private Double generateRandomValue(Sensor.SensorType sensorType) {
        switch (sensorType) {
            case TEMPERATURE:
                return 15.0 + random.nextDouble() * 10.0; // Random temperature between 15 and 25
            case HUMIDITY:
                return 30.0 + random.nextDouble() * 40.0; // Random humidity between 30 and 70
            case PRESSURE:
                return 950.0 + random.nextDouble() * 50.0; // Random pressure between 950 and 1000
            default:
                throw new IllegalArgumentException("Unsupported sensor type: " + sensorType);
        }
    }
}

