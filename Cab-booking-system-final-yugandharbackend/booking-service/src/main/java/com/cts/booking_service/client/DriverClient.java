package com.cts.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CabProject")
public interface DriverClient {
    @GetMapping("driver/getdrivername/{driverId}")
    public String getDriverNameByDriverId(@PathVariable Long driverId);
}
