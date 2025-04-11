package com.reactive.delivery.order_service.kafka;

import com.reactive.delivery.order_service.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    // Make sure this matches the bean name if using multiple templates
    private final ReactiveKafkaProducerTemplate<String, Order> reactiveOrderProducerTemplate;

    public Mono<Void> sendOrder(Order order) {
        return reactiveOrderProducerTemplate.send("order-events", order.getOrderId(), order)
                .doOnNext(result -> {
                    var metadata = result.recordMetadata();
                    System.out.println("Order sent to Kafka: " + metadata.topic() + " | Offset: " + metadata.offset());
                })
                .then();
    }

}
