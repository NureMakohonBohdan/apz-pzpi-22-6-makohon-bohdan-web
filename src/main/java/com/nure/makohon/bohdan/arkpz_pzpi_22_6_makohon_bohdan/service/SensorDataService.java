package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.SensorDataDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Sensor;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SensorData;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorDataRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public SensorData createSensorData(Integer sensorId, SensorDataDTO sensorDataDTO) {
        // Fetch the sensor
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() ->
                new IllegalArgumentException("Sensor with ID " + sensorId + " not found"));

        // Convert DTO to Entity
        SensorData sensorData = new SensorData();
        sensorData.setSensor(sensor);

        // Save the sensor data
        return sensorDataRepository.save(sensorData);
    }

    public List<SensorData> findAllSensorData() {
        return sensorDataRepository.findAll();
    }

    public Optional<SensorData> findSensorDataById(Long id) {
        return sensorDataRepository.findById(id);
    }

    public List<SensorData> findSensorDataBySensorId(Integer sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }

    public SensorData saveSensorData(SensorData sensorData) {
        return sensorDataRepository.save(sensorData);
    }

    public void deleteSensorDataById(Long id) {
        sensorDataRepository.deleteById(id);
    }
}
