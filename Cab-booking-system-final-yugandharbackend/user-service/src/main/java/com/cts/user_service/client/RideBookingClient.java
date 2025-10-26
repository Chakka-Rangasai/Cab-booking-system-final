package com.cts.user_service.client;

import com.cts.user_service.dto.RideDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface RideBookingClient {
    @PostMapping("ride/create")
    public ResponseEntity<RideDetailsDto> createRide(@RequestBody RideDetailsDto request);
    @GetMapping("ride/user/{userId}/confirmed")
    public ResponseEntity<List<RideDetailsDto>> getConfirmedRequestsByUser(@PathVariable Long userId);
    @GetMapping("ride/user/{userId}/request/{requestId}/confirmed")
    public ResponseEntity<RideDetailsDto> getConfirmedRideForUser(@PathVariable Long userId,@PathVariable Long requestId);

}