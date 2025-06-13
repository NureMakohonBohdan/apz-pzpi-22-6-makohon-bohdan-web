package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationDTO {
    @NotNull(message = "Notification type is required")
    private String notificationType;

    @NotNull(message = "Value is required")
    private Double value;

    @NotNull(message = "Threshold is required")
    private Double threshold;

}
