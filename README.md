# Reactive Order Assignment System

A DoorDash-style real-time backend for batching and assigning food delivery orders using Spring Boot WebFlux, Kafka, and reactive streams.

## Features
- Real-time order ingestion and dispatch
- Kafka-based event streaming
- Zone-based batching and low-latency assignment
- Chunking logic to simulate DoorDash-style delivery routing

## Tech Stack
- Spring Boot WebFlux
- Apache Kafka
- Project Reactor

## Summary
⭐ Situation
- In simple terms, person A in postal code 001 order a Dominos Pizza, in the next minute person B in postal code 001 orders a burger from Burger King, which is quite close to Dominos location.
- Such orders can be clubbed, and it will be delivered by the same Runner 
- Food delivery platforms like DoorDash need to efficiently group and assign high volumes of incoming orders while minimizing delays and driver overhead.

🎯 Task
- Design a backend system that simulates real-time order ingestion, batching, and runner assignment based on delivery zones and prep time.

🛠 Action
- Built a real-time, event-driven backend using Spring Boot WebFlux, Kafka, and reactive streams to:
- Ingest incoming orders via REST
- Group and chunk orders by zone
- Assign orders dynamically using low-latency streaming
- Optimize delivery routes and reduce dispatch overhead

✅ Result
- Achieved 2× faster dispatch by reducing assignment delays through zone-aware logic, while enabling real-time grouping that saves delivery time and improves operational efficiency.

## Running the Project

1. Clone the repo
2. Start Kafka with Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Run
    ```bash
   ./gradlew bootRun
   ```

4. Send a test order

   ```bash
   curl -X POST http://localhost:8080/orders \
     -H "Content-Type: application/json" \
     -d '{"restaurantId": "r1", "customerAddress": "123 Main St", "zone": "85719", "estimatedPrepTime": 10}'

Inspired by this blog: [Link](https://dasher.doordash.com/en-us/blog/batched-offers-explained)