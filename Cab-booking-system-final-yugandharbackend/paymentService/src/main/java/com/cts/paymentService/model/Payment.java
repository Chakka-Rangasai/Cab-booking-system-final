package com.cts.paymentService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String method; // "CARD" or "UPI"
    private String status; // "SUCCESS", "FAILED", etc.
    private double amount; // Payment amount
    private Long requestId;

}
