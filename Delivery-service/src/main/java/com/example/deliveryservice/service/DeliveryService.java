package com.example.deliveryservice.service;

import com.example.deliveryservice.util.DeliveryStatus;

import java.time.LocalTime;

public class DeliveryService {
    public DeliveryStatus getDeliveryStatus() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(19, 0);
        if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
            return DeliveryStatus.CAN_BE_DELIVERED;
        } else {
            return DeliveryStatus.CANNOT_BE_DELIVERED;
        }
    }
}
