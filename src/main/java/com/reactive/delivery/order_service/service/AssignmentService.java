package com.reactive.delivery.order_service.service;

import com.reactive.delivery.order_service.kafka.AssignmentProducer;
import com.reactive.delivery.order_service.model.Assignment;
import com.reactive.delivery.order_service.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AssignmentService {

    private final AssignmentProducer assignmentProducer;
    private final Sinks.Many<Order> orderSink;

    public AssignmentService(AssignmentProducer assignmentProducer) {
        this.assignmentProducer = assignmentProducer;
        this.orderSink = Sinks.many().multicast().onBackpressureBuffer();

        orderSink.asFlux()
                .groupBy(Order::getZone) // Group by zone - can be a zipcode
                .flatMap(zoneFlux ->
                        zoneFlux
                                .bufferTimeout(5, Duration.ofSeconds(10)) // batch per zone
                                .filter(batch -> !batch.isEmpty())
                                .flatMap(batch -> {
                                    String zone = batch.getFirst().getZone(); // all orders in this batch have same zone
                                    String runnerId = "runner-" + zone; // simulated zone-based runner

                                    System.out.println("Assigning " + batch.size() + " orders for zone " + zone + " to runner: " + runnerId);
                                    batch.forEach(order -> System.out.println("   --> Order: " + order.getOrderId()));

                                    Assignment assignment = new Assignment(UUID.randomUUID().toString(), runnerId, batch);
                                    return assignmentProducer.sendAssignment(assignment);
                                })
                )
                .subscribe();
    }

    public void bufferOrder(Order order) {
        log.info("Buffering order: {}", order.getOrderId());
        orderSink.tryEmitNext(order);
    }

    private void processBatch(List<Order> orders) {
        if (!orders.isEmpty()) {
            log.info("Assigning {} orders in batch", orders.size());
            String runnerId = "runner-001"; // Simulated runner
            Assignment assignment = new Assignment(UUID.randomUUID().toString(), runnerId, orders);
            assignmentProducer.sendAssignment(assignment).subscribe();
        }
    }
}
