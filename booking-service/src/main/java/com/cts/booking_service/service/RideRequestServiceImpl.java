package com.cts.booking_service.service;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.repository.RideRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideRequestServiceImpl implements RideRequestService{
    @Autowired
    private RideRequestRepository rideRepo;
    @Override
    public RideRequest saveRideDetails(RideRequest rideDetails) {

        // Set default status
        rideDetails.setStatus("PENDING");
        rideDetails.setAccepted(false);
        rideDetails.setAcceptedDriverId(null);

        // Save to database
        return rideRepo.save(rideDetails);

    }

    @Override
    public List<RideRequest> getPendingRides() {
        return rideRepo.findByStatus("PENDING");
    }

    @Override
    public RideRequest acceptRequest(Long requestId, Long driverId) {
        RideRequest request = rideRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setAccepted(true);
        request.setAcceptedDriverId(driverId);
        request.setStatus("CONFIRMED"); // Update status on acceptance

        return rideRepo.save(request);
    }

    @Override
    public List<RideRequest> getConfirmedRidesByDriverId(Long driverId) {
        return rideRepo.findByAcceptedDriverIdAndStatus(driverId, "CONFIRMED");
    }

    @Override
    public List<RideRequest> getConfirmedRequestsByUserId(Long userId) {
        return rideRepo.findByUserIdAndStatus(userId,"CONFIRMED");
    }
    public List<RideRequest> getAllRequests() {
        return rideRepo.findAll();
    }
    @Override
    public RideRequest getConfirmedRideForUser(Long requestId, Long userId) {
        List<RideRequest> rides = rideRepo.findByRequestIdAndUserId(requestId, userId);

        if (rides.isEmpty()) {
            throw new RuntimeException("No confirmed ride found for this user and request ID");
        }

        return rides.get(0); // Assuming only one match is expected
    }

}
