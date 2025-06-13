package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.AnalyticsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnalyticsRequestRepository extends JpaRepository<AnalyticsRequest, Long> {
    List<AnalyticsRequest> findByUserUserId(Integer userId);
}

