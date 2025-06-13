package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller;

import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.AnalyticsRequestDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.AnalyticsRequest;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.AnalyticsRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics-requests")
public class AnalyticsRequestController {

    @Autowired
    private AnalyticsRequestService analyticsRequestService;

    // GET: Retrieve all analytics requests
    @GetMapping
    public ResponseEntity<List<AnalyticsRequest>> getAllAnalyticsRequests() {
        List<AnalyticsRequest> analyticsRequests = analyticsRequestService.getAllAnalyticsRequests();
        return ResponseEntity.ok(analyticsRequests);
    }

    // GET: Retrieve an analytics request by ID
    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsRequest> getAnalyticsRequestById(@PathVariable Long id) {
        return analyticsRequestService.getAnalyticsRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Retrieve all analytics requests for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnalyticsRequest>> getAnalyticsRequestsByUserId(@PathVariable Integer userId) {
        List<AnalyticsRequest> analyticsRequests = analyticsRequestService.getAnalyticsRequestsByUserId(userId);
        return ResponseEntity.ok(analyticsRequests);
    }

    // POST: Create a new analytics request for a specific user
    @PostMapping("/user/{userId}")
    public ResponseEntity<AnalyticsRequest> createAnalyticsRequest(
            @PathVariable Integer userId,
            @Valid @RequestBody AnalyticsRequestDTO analyticsRequestDTO) {
        AnalyticsRequest savedAnalyticsRequest = analyticsRequestService.createAnalyticsRequest(userId, analyticsRequestDTO);
        return ResponseEntity.ok(savedAnalyticsRequest);
    }

    // DELETE: Remove an analytics request for a specific user by request ID
    @DeleteMapping("/user/{userId}/{requestId}")
    public ResponseEntity<Void> deleteAnalyticsRequest(
            @PathVariable Integer userId,
            @PathVariable Long requestId) {
        if (analyticsRequestService.deleteAnalyticsRequestByUserId(userId, requestId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}