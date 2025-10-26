package com.cts.cabproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.cabproject.entity.DriverReview;

@Repository
public interface DriverReviewRepository extends JpaRepository<DriverReview, Long> {
    
    List<DriverReview> findByDriverDriverId(Long driverId);
    
    @Query("SELECT AVG(dr.rating) FROM DriverReview dr WHERE dr.driver.driverId = :driverId")
    Double findAverageRatingByDriverId(@Param("driverId") Long driverId);
    
}
