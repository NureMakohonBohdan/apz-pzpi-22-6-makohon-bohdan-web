package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;

import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.AnalyticsRequestDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.AnalyticsRequest;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.AnalyticsRequestRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsRequestService {

    @Autowired
    private AnalyticsRequestRepository analyticsRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Retrieve all analytics requests
    public List<AnalyticsRequest> getAllAnalyticsRequests() {
        return analyticsRequestRepository.findAll();
    }

    // Retrieve an analytics request by ID
    public Optional<AnalyticsRequest> getAnalyticsRequestById(Long id) {
        return analyticsRequestRepository.findById(id);
    }

    // Retrieve all analytics requests for a specific user
    public List<AnalyticsRequest> getAnalyticsRequestsByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " not found"));

        return analyticsRequestRepository.findByUserUserId(user.getUserId());
    }

    // Create a new analytics request for a specific user
    public AnalyticsRequest createAnalyticsRequest(Integer userId, AnalyticsRequestDTO analyticsRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " not found"));

        AnalyticsRequest analyticsRequest = new AnalyticsRequest();
        analyticsRequest.setUser(user);
        analyticsRequest.setStartDate(analyticsRequestDTO.getStartDate());
        analyticsRequest.setEndDate(analyticsRequestDTO.getEndDate());
        analyticsRequest.setRequestType(AnalyticsRequest.RequestType.valueOf(analyticsRequestDTO.getRequestType()));
        analyticsRequest.setStatus(AnalyticsRequest.RequestStatus.PENDING);

        return analyticsRequestRepository.save(analyticsRequest);
    }

    // Delete an analytics request by ID for a specific user
    public boolean deleteAnalyticsRequestByUserId(Integer userId, Long requestId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " not found"));

        AnalyticsRequest request = analyticsRequestRepository.findById(requestId).orElseThrow(() ->
                new IllegalArgumentException("Analytics Request with ID " + requestId + " not found"));

        if (!request.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("Request does not belong to the specified user");
        }

        analyticsRequestRepository.delete(request);
        return true;
    }
}