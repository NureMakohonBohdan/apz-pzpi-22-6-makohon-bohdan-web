package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.SensorDTO;

import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Sensor;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SensorData;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        },
        allowedHeaders = "*"
)
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // GET: Retrieve all sensors
    @GetMapping
    public List<SensorDTO> getAllSensors() {
        return sensorService.findAllSensors().stream()
                .map(sensorService::convertToDTO)
                .toList();
    }

    // GET: Retrieve a sensor by ID
    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable Integer id) {
        return sensorService.findSensorById(id)
                .map(sensor -> ResponseEntity.ok(sensorService.convertToDTO(sensor)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Create a new sensor
    @PostMapping("/user/{userId}")
    public ResponseEntity<SensorDTO> createSensor(
            @PathVariable Integer userId,
            @Valid @RequestBody SensorDTO sensorDTO) {
        Sensor savedSensor = sensorService.createSensor(userId, sensorDTO);
        return ResponseEntity.ok(sensorService.convertToDTO(savedSensor));
    }

    // PUT: Update an existing sensor
    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> updateSensor(
            @PathVariable Integer id,
            @Valid @RequestBody SensorDTO sensorDTO) {
        return sensorService.findSensorById(id).map(sensor -> {
            sensor.setLocation(sensorDTO.getLocation());
            sensor.setSensorType(Sensor.SensorType.valueOf(sensorDTO.getSensorType().toUpperCase()));
            Sensor updatedSensor = sensorService.saveSensor(sensor);
            return ResponseEntity.ok(sensorService.convertToDTO(updatedSensor));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remove a sensor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer id) {
        if (sensorService.findSensorById(id).isPresent()) {
            sensorService.deleteSensorById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET: Retrieve all sensors for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SensorDTO>> getSensorsByUserId(@PathVariable Integer userId) {
        List<SensorDTO> sensors = sensorService.getSensorsByUserId(userId).stream()
                .map(sensorService::convertToDTO)
                .toList();
        return ResponseEntity.ok(sensors);

    }

    // GET: Retrieve all data for a specific sensor
    @GetMapping("/{id}/data")
    public ResponseEntity<List<SensorData>> getSensorData(@PathVariable Integer id) {
        List<SensorData> data = sensorService.getSensorDataBySensorId(id);
        return ResponseEntity.ok(data);
    }

}