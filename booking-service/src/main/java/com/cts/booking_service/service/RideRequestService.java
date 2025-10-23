package com.cts.booking_service.service;

import com.cts.booking_service.Entity.RideRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RideRequestService {
    RideRequest saveRideDetails(RideRequest rideDetails);
    List<RideRequest> getPendingRides();
    RideRequest acceptRequest(Long requestId, Long driverId);
    List<RideRequest> getConfirmedRidesByDriverId(Long driverId);
    List<RideRequest> getConfirmedRequestsByUserId(Long userId);
    List<RideRequest> getAllRequests();
    public RideRequest getConfirmedRideForUser(Long requestId, Long userId);
}
