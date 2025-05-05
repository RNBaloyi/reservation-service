# Reservation Microservice

This microservice handles customer reservations for restaurant tables. It integrates with the Table Microservice to determine table availability and manages a dynamic waitlist for full capacity scenarios.

---

## Tech Stack

- Java 21
- Spring Boot
- RESTful APIs
- Maven
- Openfeign

---

## Responsibilities

- Process table reservation and cancellation requests
- Interact with Table Microservice to verify table status
- Manage an in-memory waitlist queue
- Prioritize customers based on preferences

---

## Project Structure

com.restaurant.reservation
├── client # Feign/Rest clients to communicate with other services
├── config # Application configuration (e.g., Feign config, RestTemplate beans)
├── controller # REST API for reservations
├── dto # Data Transfer Objects for request/response bodies
├── exception # Global and custom exception handlers
├── model # Reservation and Waitlist entities or structures
├── service # Business logic (reservation + waitlist)
└── ReservationServiceApplication.java

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/RNBaloyi/reservation-service
cd reservation-microservice
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```
The service will start on port 8082.

### 3. H2 Database Access
The app uses an in-memory H2 database that initializes automatically on startup.

H2 Console URL: http://localhost:8082/h2-console

JDBC URL: jdbc:h2:mem:reservationsdb

Username: sa

Password: (leave blank)

> Make sure the **Table Microservice** is running first.

---

## API Endpoint

### Make or Cancel Reservation

**Request:**

```http
POST /reservations
```

**Body:**

```json
{
  "tableId": "T123",
  "customerId": "C456",
  "reservationType": "reserve",
  "preferences": ["Window", "Quiet"]
}
```

---

### ✅ Reservation Successful

```json
{
  "reservationId": "R001",
  "status": "success",
  "tableId": "T123"
}
```

---

### Waitlisted Response

```json
{
  "reservationId": null,
  "status": "waitlisted",
  "waitlistMessage": "You are #2 in the queue."
}
```

---

## Error Handling

- `404 Not Found` – Table not found
- `400 Bad Request` – Invalid reservation type
- `500 Internal Server Error` – Reservation processing failed

---


