package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.SensorDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Sensor;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SensorData;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorDataRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;


    public List<Sensor> findAllSensors() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> findSensorById(Integer id) {
        return sensorRepository.findById(id);
    }

    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Transactional
    public void deleteSensorById(Integer id) {
        sensorDataRepository.deleteAllBySensorId(id);
        sensorRepository.deleteById(id);
    }


    public Sensor createSensor(Integer userId, SensorDTO sensorDTO) {
        // Fetch the user
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " not found"));

        // Convert DTO to Entity
        Sensor sensor = new Sensor();
        sensor.setLocation(sensorDTO.getLocation());
        sensor.setSensorType(Sensor.SensorType.valueOf(sensorDTO.getSensorType().toUpperCase()));
        sensor.setUser(user);

        // Save the sensor
        return sensorRepository.save(sensor);
    }
    public List<Sensor> getSensorsByUserId(Integer userId) {
        return sensorRepository.findByUserUserId(userId);
    }

    // Convert Sensor to SensorDTO
    public SensorDTO convertToDTO(Sensor sensor) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setId(sensor.getId());
        sensorDTO.setLocation(sensor.getLocation());
        sensorDTO.setSensorType(sensor.getSensorType().toString());
        return sensorDTO;
    }

    public List<SensorData> getSensorDataBySensorId(Integer sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }

}
