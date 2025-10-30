package com.cts.user_service.client;

import com.cts.user_service.dto.RideDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface RideBookingClient {
    @PostMapping("ride/create")
    public ResponseEntity<RideDetailsDto> createRide(@RequestHeader("Authorization") String token, @RequestBody RideDetailsDto request);
    @GetMapping("ride/user/{userId}/confirmed")
    public ResponseEntity<List<RideDetailsDto>> getConfirmedRequestsByUser(@RequestHeader("Authorization") String token,@PathVariable Long userId);
    @GetMapping("ride/user/{userId}/request/{requestId}/confirmed")
    public ResponseEntity<RideDetailsDto> getConfirmedRideForUser(@RequestHeader("Authorization") String token,@PathVariable Long userId,@PathVariable Long requestId);

}