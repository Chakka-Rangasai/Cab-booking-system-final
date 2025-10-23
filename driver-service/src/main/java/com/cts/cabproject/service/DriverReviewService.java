package com.cts.cabproject.service;

import java.util.List;

import com.cts.cabproject.entity.DriverReview;

public interface DriverReviewService {
    
    DriverReview addReview(DriverReview review);
    
    List<DriverReview> getReviewsByDriverId(Long driverId);
    
    Double getAverageRating(Long driverId);
    
   
}
