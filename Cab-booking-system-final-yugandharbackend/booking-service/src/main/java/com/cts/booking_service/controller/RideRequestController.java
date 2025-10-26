package com.cts.booking_service.controller;

import java.util.List;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.dto.RideHistory;
import com.cts.booking_service.service.RideRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/ride")
public class RideRequestController {

    @Autowired
    private RideRequestService rideRequestService;

    @PostMapping("/create")
    public ResponseEntity<RideRequest> createRide(@RequestBody RideRequest request) {
       RideRequest rideDetails= rideRequestService.saveRideDetails(request);
       return ResponseEntity.ok(rideDetails);
    }
    @GetMapping("/pending")
    public List<RideRequest> getPendingRides() {
        return rideRequestService.getPendingRides();
    }

    @GetMapping ("/accept/{requestId}/{driverId}")
    public ResponseEntity<RideRequest> acceptRide(@PathVariable Long requestId, @PathVariable Long driverId) {
        RideRequest updatedRequest = rideRequestService.acceptRequest(requestId, driverId);
        return ResponseEntity.ok(updatedRequest);
    }
    @GetMapping("/driver/{driverId}/confirmed")
    public ResponseEntity<List<RideRequest>> getConfirmedRidesByDriver(@PathVariable Long driverId) {
        List<RideRequest> confirmedRides = rideRequestService.getConfirmedRidesByDriverId(driverId);
        return ResponseEntity.ok(confirmedRides);
    }
    @GetMapping("/user/{userId}/confirmed")
    public ResponseEntity<List<RideHistory>> getConfirmedRequestsByUser(@PathVariable Long userId) {
        List<RideHistory> confirmedRequests = rideRequestService.getConfirmedRequestsByUserId(userId);
        return ResponseEntity.ok(confirmedRequests);
    }
    @GetMapping("/all")
    public ResponseEntity<List<RideRequest>> getAllRequests() {
        return ResponseEntity.ok(rideRequestService.getAllRequests());
    }
    @GetMapping("/user/{userId}/request/{requestId}/confirmed")
    public ResponseEntity<RideRequest> getConfirmedRideForUser(
            @PathVariable Long userId,
            @PathVariable Long requestId) {
        RideRequest ride = rideRequestService.getConfirmedRideForUser(requestId, userId);
        return ResponseEntity.ok(ride);
    }

}


