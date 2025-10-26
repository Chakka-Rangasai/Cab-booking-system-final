package com.cts.cabproject.service;

import java.util.List;
import java.util.Map;

import com.cts.cabproject.dto.RideDetailsDto;
import com.cts.cabproject.entity.Driver;
import org.springframework.http.ResponseEntity;

public interface DriverService {

    ResponseEntity<RideDetailsDto> acceptRide(Long rideId,Long driverID);
    Driver registerDriver(Driver driver);
    
    Driver searchByID(Long driverId);
    
    Driver updateProfile(Driver driver);
    
    void deleteDriver(Long driverId);
    
    List<Driver> getAllDrivers(int pageNo, int pageSize, String sortBy);
    
    void updateAvailability(Long driverId, boolean isAvailable);
    
    Driver validateDriverLogin(String email, String password);
    
    Driver findDriverByEmailAndPassword(String email, String passwordHash);
    ResponseEntity<Map<String, String>> verifyForgotPasswordCredentials(Driver forgotPasswordCredentials);
    ResponseEntity<Map<String,String>> updateNewPassword(Driver driverNewPassword);
    List<RideDetailsDto> getConfirmedRidesByDriverId(Long driverId);
    List<RideDetailsDto> getPendingRides();
    Driver getByDriverId(Long driverId);
    String getDriverNameByDriverId(Long driverId);
}
