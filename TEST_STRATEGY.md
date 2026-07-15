# Wallet Transfer Service - Test Strategy

## Objective

The objective of this automation suite is to validate the Wallet Transfer Service across multiple layers, ensuring correctness of API behavior, business workflow, database persistence, and reliability.

---

# Test Levels Covered

## 1. API Validation

The following API scenarios are automated:

- Successful wallet transfer
- Insufficient balance
- Negative amount validation
- Same source and destination wallet
- Wallet not found
- Duplicate transfer request

Validation includes:

- HTTP status codes
- Response payload
- Error messages
- Business status

---

## 2. Business Workflow Validation

The automation validates the following business rules:

- Source wallet is debited once
- Destination wallet is credited once
- Successful transfer is persisted
- Failed transfer does not update balances
- Duplicate transfer does not create duplicate side effects

---

## 3. Database Validation

The following database validations are performed using JDBC.

### Wallet Table

Validated fields:

- Balance

### Transfer Table

Validated fields:

- Reference
- Status

The database state is compared with the API response to ensure consistency.

---

# Test Architecture

The automation framework follows a layered design.

```
Tests
   │
   ▼
WalletClient
   │
   ▼
REST API
   │
   ▼
Wallet Service
   │
   ▼
MySQL Database
```

### BaseTest

Responsible for RestAssured configuration.

### WalletClient

Encapsulates API requests.

### DatabaseUtils

Performs database validation.

### WalletTransferTest

Contains business scenarios.

---

# Test Data Management

Before every test:

- Wallet 1 balance is reset to 10000
- Wallet 2 balance is reset to 5000

Each transfer uses a unique transaction reference generated using the current timestamp to avoid duplicate execution across test runs.

---

# Reliability Testing

## Duplicate Transfer

The same transfer request is submitted twice.

Expected outcome:

- Money is transferred once
- One transfer record exists
- No duplicate debit
- No duplicate credit

---

## Concurrent Transfer

Two concurrent transfer requests are executed using Java Threads.

Validated conditions:

- Source balance never becomes negative
- Destination balance never exceeds one successful transfer

---

# Assumptions

The following assumptions were made:

- Transfer reference acts as the idempotency identifier.
- Wallet data is pre-seeded.
- Local MySQL database is available.
- Application is running locally.

---

# Out of Scope

The sample application does not include:

- Kafka
- RabbitMQ
- Outbox Pattern
- Audit/Event Tables
- Event Publishing
- Distributed Transactions

Therefore, these validations are documented but not automated.

---

# Future Improvements

With additional implementation time, the following improvements could be added:

- TestContainers
- Dockerized execution
- GitHub Actions CI/CD
- API Contract Testing
- Performance Testing
- Security Testing
- Kafka Event Validation
- Audit Trail Validation

---

# Summary

This automation suite validates the Wallet Transfer Service from API to database while covering business rules, reliability scenarios, duplicate handling, and concurrency. The framework is designed to be modular, maintainable, and easily extensible for future enhancements.