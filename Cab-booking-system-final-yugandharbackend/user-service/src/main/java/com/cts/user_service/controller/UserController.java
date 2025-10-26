package com.cts.user_service.controller;
import com.cts.user_service.dto.RideDetailsDto;
import com.cts.user_service.dto.UserLoginDto;
import com.cts.user_service.dto.UserDto;
import com.cts.user_service.entity.UserEntity;
import com.cts.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
 public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto loginData) {
        return userService.validateLoginData(loginData);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody UserDto forgotPasswordCredentials) {
        return userService.verifyForgotPasswordCredentials(forgotPasswordCredentials);
    }

    @PutMapping("/changepassword")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody UserEntity userNewPassword) {
        return userService.updateNewPassword(userNewPassword);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/editprofile")
    public ResponseEntity<Map<String,String>> editUserProfileDetails(@RequestBody UserDto userProfile) {
        System.out.println("editUserProfileDetails :: controller");
        return userService.editUserProfileData(userProfile);
    }
    @GetMapping("/{userId}/confirmed")
    public ResponseEntity<Map<String, Object>> getConfirmedRequestsByUser(@PathVariable Long userId){
        return userService.getConfirmedRequestsByUser(userId);
    }
    @PostMapping("/createride")
    public ResponseEntity<RideDetailsDto>  createRide(@RequestBody RideDetailsDto rideDetails){
        return userService.createRideRequest(rideDetails);
    }
    @GetMapping("/{userId}/request/{requestId}")
    public ResponseEntity<RideDetailsDto> getConfirmedRideForUser(@PathVariable Long userId,@PathVariable Long requestId){
        return userService.getConfirmedRideForUser(userId,requestId);
    }

}
