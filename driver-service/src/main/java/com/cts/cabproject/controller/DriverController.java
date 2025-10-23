package com.cts.cabproject.controller;

import com.cts.cabproject.dto.RideDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.cabproject.entity.Driver;
import com.cts.cabproject.service.DriverService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//(origins = "http://localhost:4200")
@RequestMapping("/driver")
public class DriverController {
    
    @Autowired
    private DriverService driverService;
    
    // Driver Registration - Public endpoint (no authentication required)
    @PostMapping("/register")
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {
        // Auto-verify new drivers and set them as available
        driver.setVerified(true);
        driver.setAvailable(true);
        return ResponseEntity.ok(driverService.registerDriver(driver));
    }
    
    @GetMapping("getProfileDetails/{DriverId}")
    public ResponseEntity<Driver> profileView(@PathVariable Long DriverId){
        return ResponseEntity.ok(driverService.searchByID(DriverId));
    }
    
    @PostMapping("/update")
    public ResponseEntity<Driver> updateProfile(@RequestBody Driver driver){
        return ResponseEntity.ok(driverService.updateProfile(driver));
    }
  
    // Protected endpoints - require JWT authentication
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{driverId}/{isAvailable}")
    public ResponseEntity<String>  toggleAvailability(@PathVariable Long driverId, @PathVariable boolean isAvailable) {
        driverService.updateAvailability(driverId, isAvailable);
        return ResponseEntity.ok("Availability updated");

    }
    @PostMapping("/forgotpassword")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody Driver forgotPasswordCredentials) {
        return driverService.verifyForgotPasswordCredentials(forgotPasswordCredentials);
    }
    @PutMapping("/changepassword")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Driver driverNewPassword) {
        return driverService.updateNewPassword(driverNewPassword);
    }
    @GetMapping ("/acceptride/{requestId}/{driverId}")
    public ResponseEntity<RideDetailsDto> acceptRide(@PathVariable Long requestId, @PathVariable Long driverId){
             return driverService.acceptRide(requestId,driverId);
    }
    @GetMapping("/{driverId}/confirmed")
    public ResponseEntity<List<RideDetailsDto>> getConfirmedRidesByDriver(@PathVariable Long driverId) {
        List<RideDetailsDto> confirmedRides = driverService.getConfirmedRidesByDriverId(driverId);
        return ResponseEntity.ok(confirmedRides);
    }
    @GetMapping("/pending")
    public List<RideDetailsDto> getPendingRides(){
        return driverService.getPendingRides();
    }
    @GetMapping("/{driverId}")
    public Driver getDriverById(@PathVariable Long driverId){
        return driverService.getByDriverId(driverId);
    }

}


