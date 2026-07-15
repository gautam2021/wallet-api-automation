# Wallet Transfer Service - SDET Assignment

## Overview

This project demonstrates an end-to-end automation testing solution for a Wallet Transfer Service.

The automation validates the complete flow from API request to database persistence while ensuring business rules such as balance consistency, duplicate transfer handling, and validation failures.

---

# Technology Stack

- Java 25
- Spring Boot 3.x
- Maven
- Rest Assured
- JUnit 5
- MySQL
- JDBC

---

# Project Structure

```
gautam2021
│
├── wallet-service
│      Spring Boot REST API
│
├── wallet-transfer-tests
│      BaseTest
│      WalletClient
│      DatabaseUtils
│      WalletTransferTest
│
└── README.md
```

---

# Test Coverage

## API Validation

- Successful wallet transfer
- Insufficient balance
- Negative amount validation
- Same source and destination wallet validation
- Wallet not found validation
- Duplicate transfer validation

---

## Database Validation

The automation validates:

- Source wallet balance
- Destination wallet balance
- Transfer status
- Transfer persistence

---

## Business Workflow Validation

The following business rules are verified:

- Source wallet debited once
- Destination wallet credited once
- Transfer status is SUCCESS
- Failed transfers do not modify balances
- Duplicate requests do not create duplicate transfers

---

## Reliability Testing

Implemented:

- Duplicate transfer validation
- Basic concurrent transfer validation using Java Threads

---

## Project Architecture

### wallet-service

Contains the Spring Boot REST service.

### wallet-transfer-tests

Contains

- BaseTest
- WalletClient
- DatabaseUtils
- API Test Suite

---

# Running the Application

Start MySQL.

Run the Wallet Service.

```bash
cd wallet-service

mvn spring-boot:run
```

---

# Running Tests

```bash
cd wallet-transfer-tests

mvn clean test
```

---

# Assumptions

- MySQL database is running locally.
- Wallet data is pre-seeded.
- Transfer Reference is used as the idempotency identifier.
- Database is reset before every test execution.

---

# Known Limitations

The sample Wallet Service does not implement:

- Kafka
- RabbitMQ
- Outbox Pattern
- Audit Table
- Event Publishing
- Idempotency-Key HTTP Header

Therefore, these validations are documented but considered out of scope for this assignment.

---

# Future Improvements

Given additional time, the following enhancements could be added:

- TestContainers
- Docker Compose
- GitHub Actions CI
- API Contract Testing
- Performance Testing
- Audit/Event Validation
- Message Queue Validation

---

# Author

**Mohit Kumar Gautam**

Software Development Engineer in Test (SDET) Assignment