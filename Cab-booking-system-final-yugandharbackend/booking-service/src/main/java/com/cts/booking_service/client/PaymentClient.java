package com.cts.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="paymentService")
public interface PaymentClient {
    @GetMapping("payment/getpaymentstatusforride/{requestId}")
    public List<String> getPaymentDetailsForRide(@PathVariable Long requestId);
}
