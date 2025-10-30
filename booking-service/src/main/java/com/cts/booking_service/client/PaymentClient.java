package com.cts.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="paymentService")
public interface PaymentClient {
    @GetMapping("payment/getpaymentstatusforride/{requestId}")
    public List<String> getPaymentDetailsForRide(@RequestHeader("Authorization") String token, @PathVariable Long requestId);
}
