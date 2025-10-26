package com.cts.cabproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.cabproject.entity.Driver;
import com.cts.cabproject.entity.DriverReview;
import com.cts.cabproject.service.DriverReviewService;
import com.cts.cabproject.service.DriverService;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class DriverReviewController {
    
    @Autowired
    private DriverReviewService reviewService;
    
    @Autowired
    private DriverService ds;
    
    @PostMapping("/{driverId}")
    public ResponseEntity<DriverReview> addReview(@RequestBody DriverReview review, @PathVariable("driverId") Long driverId) {
    	Driver d1=ds.searchByID(driverId);
        review.setDriver(d1); // ensure driverId is se
        DriverReview savedReview = reviewService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<DriverReview>> getDriverReviews(@PathVariable Long driverId) {
        List<DriverReview> reviews = reviewService.getReviewsByDriverId(driverId);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/driver/{driverId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long driverId) {
        Double averageRating = reviewService.getAverageRating(driverId);
        return ResponseEntity.ok(averageRating != null ? averageRating : 0.0);
    }
    
    
}
