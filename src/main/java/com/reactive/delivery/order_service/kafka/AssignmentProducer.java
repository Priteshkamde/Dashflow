package com.reactive.delivery.order_service.kafka;

import com.reactive.delivery.order_service.model.Assignment;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AssignmentProducer {

    private final ReactiveKafkaProducerTemplate<String, Assignment> kafkaTemplate;

    public Mono<Void> sendAssignment(Assignment assignment) {
        return kafkaTemplate.send("assignment-events", assignment.getBatchId(), assignment)
                .doOnNext(result -> System.out.println("Sent assignment: " + assignment.getBatchId()))
                .then();
    }
}
