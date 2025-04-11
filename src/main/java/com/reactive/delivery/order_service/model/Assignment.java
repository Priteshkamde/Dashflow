package com.reactive.delivery.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Assignment {
    private String batchId = UUID.randomUUID().toString();
    private String runnerId;
    private List<Order> orders;
}
