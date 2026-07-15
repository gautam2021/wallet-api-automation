# Wallet Transfer Service - SDET Assignment

## Overview

This project demonstrates an end-to-end automation testing solution for a Wallet Transfer Service using Java, Spring Boot, Rest Assured, JUnit 5, and MySQL.

The solution validates API responses, business workflows, database state, idempotency behavior, and basic concurrency scenarios.

---

## Tech Stack

- Java 25
- Spring Boot 3
- JUnit 5
- Rest Assured
- Maven
- MySQL
- JDBC

---

## Project Structure

```
wallet-service/
    Spring Boot Wallet API

wallet-transfer-tests/
    API Automation
    Database Validation
    Test Utilities
```

---

## Test Coverage

### API Validation

- Successful money transfer
- Insufficient balance
- Negative amount
- Same source and destination wallet
- Wallet not found
- Duplicate transfer

### Database Validation

- Source wallet balance
- Destination wallet balance
- Transfer status
- Balance consistency

### Reliability

- Duplicate request handling
- Basic concurrent transfer validation

---

## Running the Application

Start MySQL.

Run the Wallet Service

```
mvn spring-boot:run
```

---

## Running Tests

```
cd wallet-transfer-tests

mvn clean test
```

---

## Assumptions

- MySQL is running locally.
- Wallets are pre-seeded.
- Transfer reference acts as the idempotency key.
- No external messaging or audit service exists.

---

## Known Limitations

The sample application does not include:

- Kafka
- RabbitMQ
- Audit Table
- Outbox Pattern
- Event Publishing

Therefore those validations are documented but out of scope.

---

## Author

Mohit Kumar Gautam