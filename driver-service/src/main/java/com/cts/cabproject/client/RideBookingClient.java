package com.cts.cabproject.client;

import com.cts.cabproject.dto.RideDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface RideBookingClient {
    @GetMapping("ride/pending")
    public List<RideDetailsDto> getPendingRides();
    @GetMapping("ride/accept/{requestId}/{driverId}")
    public ResponseEntity<RideDetailsDto> acceptRide(@PathVariable Long requestId, @PathVariable Long driverId) ;
    @GetMapping("ride/driver/{driverId}/confirmed")
    public ResponseEntity<List<RideDetailsDto>> getConfirmedRidesByDriver(@PathVariable Long driverId) ;

}
