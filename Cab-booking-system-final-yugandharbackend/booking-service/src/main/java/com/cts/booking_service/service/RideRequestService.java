package com.cts.booking_service.service;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.dto.RideHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RideRequestService {
    RideRequest saveRideDetails(RideRequest rideDetails);
    List<RideRequest> getPendingRides();
    RideRequest acceptRequest(Long requestId, Long driverId);
    List<RideRequest> getConfirmedRidesByDriverId(Long driverId);
    List<RideHistory> getConfirmedRequestsByUserId(Long userId);
    List<RideRequest> getAllRequests();
    public RideRequest getConfirmedRideForUser(Long requestId, Long userId);
}
