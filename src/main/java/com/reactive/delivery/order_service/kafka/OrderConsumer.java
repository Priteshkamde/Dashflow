package com.reactive.delivery.order_service.kafka;

import com.reactive.delivery.order_service.model.Order;
import com.reactive.delivery.order_service.service.AssignmentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final AssignmentService assignmentService;
    private final ReactiveKafkaConsumerTemplate<String, Order> reactiveOrderConsumerTemplate;

    @PostConstruct
    public void consumeOrders() {
        reactiveOrderConsumerTemplate
                .receive()
                .doOnNext(record -> {
                    Order order = record.value();
                    System.out.println("Received Order: " + order.getOrderId());
                    assignmentService.bufferOrder(order);
                })
                .subscribe();
    }
}
