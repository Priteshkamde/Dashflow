package com.reactive.delivery.order_service.controller;

import com.reactive.delivery.order_service.kafka.OrderProducer;
import com.reactive.delivery.order_service.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<String> createOrder(@RequestBody Order order) {
        return orderProducer.sendOrder(order)
                .thenReturn("Order accepted: " + order.getOrderId());
    }
}
