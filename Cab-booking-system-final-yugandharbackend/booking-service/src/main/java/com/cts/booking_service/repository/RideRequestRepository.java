package com.cts.booking_service.repository;

import com.cts.booking_service.Entity.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
    List<RideRequest> findByStatus(String status);
    List<RideRequest> findByAcceptedDriverIdAndStatus(Long driverId, String status);
    List <RideRequest> findByUserIdAndStatus(Long userId,String status);
    List <RideRequest> findByRequestIdAndUserId(Long requestId,Long userId);
}
