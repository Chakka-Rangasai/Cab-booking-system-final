package com.cts.user_service.service;


import com.cts.user_service.client.RideBookingClient;
import com.cts.user_service.dto.RideDetailsDto;
import com.cts.user_service.dto.UserLoginDto;
import com.cts.user_service.dto.UserDataDto;
import com.cts.user_service.dto.UserDto;
import com.cts.user_service.entity.UserEntity;
import com.cts.user_service.exceptions.AuthenticationException;
import com.cts.user_service.exceptions.DuplicateFieldException;
import com.cts.user_service.exceptions.ForgotPasswordVerificationException;
import com.cts.user_service.repository.UserRepository;
import com.cts.user_service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private RideBookingClient rideClient;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<Map<String, String>> registerUser(UserDto user) {
        logger.info("Attempting to register user: {}", user.getUserEmail());

        List<UserEntity> existingUsers = userRepo.findByUserNameOrUserEmailOrUserPhoneNumber(
                user.getUserName(), user.getUserEmail(), user.getUserPhoneNumber());

        Map<String, String> errors = new HashMap<>();

        for (UserEntity userPresent : existingUsers) {
            if (userPresent.getUserName().equals(user.getUserName())) {
                errors.put("userName", "Username already exists");
            }
            if (userPresent.getUserEmail().equals(user.getUserEmail())) {
                errors.put("userEmail", "Email already exists");
            }
            if (userPresent.getUserPhoneNumber().equals(user.getUserPhoneNumber())) {
                errors.put("userPhoneNumber", "Phone number already exists");
            }
        }

        if (!errors.isEmpty()) {
            logger.warn("Duplicate fields found during registration: {}", errors);
            throw new DuplicateFieldException(errors);
        }

        UserEntity newUser = new UserEntity();
        newUser.setUserName(user.getUserName());
        newUser.setUserEmail(user.getUserEmail());
        newUser.setUserPhoneNumber(user.getUserPhoneNumber());
        newUser.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        userRepo.save(newUser);
        logger.info("User registered successfully: {}", user.getUserEmail());

        Map<String, String> success = new HashMap<>();
        success.put("message", "User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }

    @Override
    public ResponseEntity<Map<String, Object>> validateLoginData(UserLoginDto loginData) {
        logger.info("Login attempt for email: {}", loginData.getEmail());

        Optional<UserEntity> userPresent = userRepo.findByUserEmail(loginData.getEmail());

        if (userPresent.isEmpty()) {
            logger.error("Login failed: User not found for email {}", loginData.getEmail());
            throw new AuthenticationException("User not found");
        }

        UserEntity user = userPresent.get();

        if (!passwordEncoder.matches(loginData.getPassword(), user.getUserPassword())) {
            logger.error("Login failed: Invalid password for email {}", loginData.getEmail());
            throw new AuthenticationException("Invalid password");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginData.getEmail(), loginData.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateJwtToken(authentication);

        UserDataDto userData = new UserDataDto();
        userData.setUserId(user.getUserId());
        userData.setUserName(user.getUserName());
        userData.setUserEmail(user.getUserEmail());
        userData.setUserPhoneNumber(user.getUserPhoneNumber());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("user", userData);

        logger.info("Login successful for user: {}", user.getUserEmail());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> verifyForgotPasswordCredentials(UserDto forgotPasswordCredentials) {
        logger.info("Verifying forgot password credentials for email: {}", forgotPasswordCredentials.getUserEmail());

        Optional<UserEntity> user = userRepo.findByUserEmailAndUserPhoneNumber(
                forgotPasswordCredentials.getUserEmail(), forgotPasswordCredentials.getUserPhoneNumber());

        if (user.isEmpty()) {
            logger.warn("Forgot password verification failed for email: {}", forgotPasswordCredentials.getUserEmail());
            throw new ForgotPasswordVerificationException("Invalid Email or Phone number");
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully");
        response.put("user_id", String.valueOf(user.get().getUserId()));

        logger.info("Forgot password verification successful for user ID: {}", user.get().getUserId());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> updateNewPassword(UserEntity userNewPassword) {
        logger.info("Password update attempt for user ID: {}", userNewPassword.getUserId());

        Optional<UserEntity> optionalUser = userRepo.findByUserId(userNewPassword.getUserId());

        if (optionalUser.isEmpty()) {
            logger.error("Password update failed: User not found for ID {}", userNewPassword.getUserId());
            throw new AuthenticationException("User not found");
        }

        UserEntity existingUser = optionalUser.get();
        existingUser.setUserPassword(passwordEncoder.encode(userNewPassword.getUserPassword()));
        userRepo.save(existingUser);

        logger.info("Password updated successfully for user ID: {}", userNewPassword.getUserId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> editUserProfileData(UserDto userProfile) {
        logger.info("Editing profile for user ID: {}", userProfile.getUserId());

        Optional<UserEntity> user = userRepo.findByUserId(userProfile.getUserId());

        if (user.isEmpty()) {
            logger.error("Edit profile failed: User not found for ID {}", userProfile.getUserId());
            throw new AuthenticationException("User not found");
        }

        UserEntity userToUpdate = user.get();

        List<UserEntity> duplicateUsers = userRepo.findByUserNameOrUserEmailOrUserPhoneNumber(
                userProfile.getUserName(), userProfile.getUserEmail(), userProfile.getUserPhoneNumber());

        Map<String, String> errors = new HashMap<>();

        for (UserEntity dup : duplicateUsers) {
            if (!dup.getUserId().equals(userProfile.getUserId())) {
                if (dup.getUserName().equals(userProfile.getUserName())) {
                    errors.put("userName", "Username already exists");
                }
                if (dup.getUserEmail().equals(userProfile.getUserEmail())) {
                    errors.put("userEmail", "Email already exists");
                }
                if (dup.getUserPhoneNumber().equals(userProfile.getUserPhoneNumber())) {
                    errors.put("userPhoneNumber", "Phone number already exists");
                }
            }
        }

        if (!errors.isEmpty()) {
            logger.warn("Edit profile failed due to duplicate fields: {}", errors);
            throw new DuplicateFieldException(errors);
        }

        userToUpdate.setUserName(userProfile.getUserName());
        userToUpdate.setUserEmail(userProfile.getUserEmail());
        userToUpdate.setUserPhoneNumber(userProfile.getUserPhoneNumber());

        userRepo.save(userToUpdate);

        logger.info("User profile updated successfully for ID: {}", userProfile.getUserId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getConfirmedRequestsByUser(Long userId) {
        logger.info("Fetching confirmed ride requests for user ID: {}", userId);

        ResponseEntity<List<RideDetailsDto>> response = rideClient.getConfirmedRequestsByUser(userId);
        List<RideDetailsDto> confirmedRides = Optional.ofNullable(response.getBody())
                .orElse(Collections.emptyList());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("rides", confirmedRides);
        responseMap.put("message", confirmedRides.isEmpty()
                ? "There are no confirmed rides yet"
                : "Confirmed rides found");

        logger.info("Confirmed rides fetched for user ID: {}. Count: {}", userId, confirmedRides.size());
        return ResponseEntity.ok(responseMap);
    }

    @Override
    public ResponseEntity<RideDetailsDto> createRideRequest(RideDetailsDto rideDetails) {
        return rideClient.createRide(rideDetails);
    }

    @Override
    public ResponseEntity<RideDetailsDto> getConfirmedRideForUser(Long userId, Long requestId) {
        return rideClient.getConfirmedRideForUser(userId,requestId);
    }
}