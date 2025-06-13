package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SensorDataDTO {
    private String timestamp; // Timestamp of the sensor reading
    private Double value;     // Value of the sensor reading
    @NotNull(message = "Temperature is required")
    private Double temperature;

    @NotNull(message = "Humidity is required")
    private Double humidity;
}
