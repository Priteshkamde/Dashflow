package com.reactive.delivery.order_service.model;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class Order {
    private String orderId = UUID.randomUUID().toString();
    private String restaurantId;
    private String customerAddress;
    private String zone; // postal code or may be some region
    private Instant createdAt = Instant.now();
}
