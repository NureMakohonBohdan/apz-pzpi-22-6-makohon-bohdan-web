package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnalyticsRequestDTO {
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @NotNull(message = "Request type is required")
    private String requestType;
}
