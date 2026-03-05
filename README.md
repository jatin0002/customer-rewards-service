# 🏆 Reward API

A Spring Boot REST API that calculates customer reward points based on
transaction history within a given date range.

------------------------------------------------------------------------

## 🚀 Features

-   Calculate reward points per transaction
-   Monthly reward aggregation
-   Total reward calculation
-   Custom date range filtering
-   Default last 3 months calculation
-   Global exception handling
-   H2 in-memory database
-   Unit & integration tests

------------------------------------------------------------------------

## 🧮 Reward Calculation Logic

-   No points for amount ≤ 50
-   1 point for every dollar spent over 50
-   2 points for every dollar spent over 100

### Example:

-   \$120 purchase:
    -   50 points (for 50-100)
    -   40 points (for 100-120 × 2)
    -   **Total = 90 points**

------------------------------------------------------------------------

## 📦 Tech Stack

-   Java 17
-   Spring Boot 4
-   Spring Data JPA
-   H2 Database
-   Hibernate
-   JUnit 5
-   Mockito
-   MockMvc
-   Maven

------------------------------------------------------------------------

## 🔗 API Endpoint

### Get Customer Rewards

GET /api/rewards/{customerId}

### Query Parameters

Parameter    Required    Format
----------- ---------- ------------
startDate       No        yyyy-MM-dd \
endDate         No        yyyy-MM-dd

### Rules

-   If both dates are null → defaults to last 3 months
-   If one date is provided → error
-   startDate must be ≤ endDate

------------------------------------------------------------------------

## 📄 Sample Response

{ "customerId": 1, "customerName": "Jatin Singh", "startDate":
"2026-01-01", "endDate": "2026-03-01", "monthlyPoints": { "2026-01": 90,
"2026-02": 40 }, "totalPoints": 130, "transactions": \[\] }

------------------------------------------------------------------------

## ❌ Error Response Format

{ "timestamp": "2026-03-03T10:42:27", "status": 404, "error": "Customer
not found with id: 99", "path": "/api/rewards/99" }

------------------------------------------------------------------------

## 🧪 Running Tests

mvn clean test

Run with coverage:

mvn clean verify

------------------------------------------------------------------------

## 🛠 Run Application

mvn spring-boot:run

Application runs at: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:rewardsdb

------------------------------------------------------------------------

## 👨‍💻 Author

Jatin Pratap Singh
