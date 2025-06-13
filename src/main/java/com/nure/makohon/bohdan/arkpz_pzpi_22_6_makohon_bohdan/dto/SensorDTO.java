package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public class SensorDTO {
    private Integer id;
    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Sensor type is required")
    private String sensorType;

    public @NotBlank(message = "Location is required") String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank(message = "Location is required") String location) {
        this.location = location;
    }

    public @NotBlank(message = "Sensor type is required") String getSensorType() {
        return sensorType;
    }

    public void setSensorType(@NotBlank(message = "Sensor type is required") String sensorType) {
        this.sensorType = sensorType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

