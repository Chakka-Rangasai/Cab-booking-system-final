package com.cts.paymentService.controller;

import com.cts.paymentService.model.Payment;
import com.cts.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/create")
    public Payment submitPayment(@RequestBody Payment payment) {
        return paymentService.submitPayment(payment);
    }
    @GetMapping("/getpaymentstatusforride/{requestId}")
    public List<String> getPaymentDetailsForRide(@PathVariable Long requestId){
        return paymentService.paymentStatusByRequestId(requestId);
    }
}
