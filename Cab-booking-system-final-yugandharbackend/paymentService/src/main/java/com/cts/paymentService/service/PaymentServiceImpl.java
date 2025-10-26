package com.cts.paymentService.service;


import com.cts.paymentService.model.Payment;
import com.cts.paymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment submitPayment(Payment payment) {
        Payment paymentDone = paymentRepository.save(payment);
        return paymentDone;

    }

    @Override
    public List<String> paymentStatusByRequestId(Long requestId) {
        Payment details=paymentRepository.findByRequestId(requestId);
        List<String> status=new ArrayList<>();
        status.add(details.getStatus());
        status.add(details.getMethod());
        return status;
    }
}
