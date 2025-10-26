package com.cts.booking_service.service;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.client.DriverClient;
import com.cts.booking_service.client.PaymentClient;
import com.cts.booking_service.dto.RideHistory;
import com.cts.booking_service.repository.RideRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideRequestServiceImpl implements RideRequestService{
    @Autowired
    private RideRequestRepository rideRepo;
    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private DriverClient driverClient;
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
    public List<RideHistory> getConfirmedRequestsByUserId(Long userId) {
       List<RideRequest> details= rideRepo.findByUserIdAndStatus(userId,"CONFIRMED");
       List<RideHistory> history=new ArrayList<>();
       if(details!=null){
               for(RideRequest request:details){
                   RideHistory history1=new RideHistory();
                   history1.setAmount(request.getAmount());
                   history1.setOrigin(request.getOrigin());
                   history1.setDestination(request.getDestination());
                   List<String> paymentDetails=paymentClient.getPaymentDetailsForRide(request.getRequestId());
                   history1.setMethod(paymentDetails.get(1));
                   history1.setStatus(paymentDetails.get(0));
                   String driverName=driverClient.getDriverNameByDriverId(request.getAcceptedDriverId());
                   history1.setDriverName(driverName);
                   history.add(history1);

               }
               return history;
       }
        return null;
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
