# 🏆 Reward API

A Spring Boot REST API that calculates customer reward points based on transaction history within a specified date range.   

-	Custom startDate–endDate range
-	Last N months calculation
-	Default last 3 months calculation
------------------------------------------------------------------------

## 🚀 Features

-   Calculate reward points per transaction
-   Monthly reward aggregation
-   Total reward calculation
-   Custom date range filtering
-   Last N months reward calculation
-   Default last 3 months calculation
-   Global exception handling
-   H2 in-memory database
-   Unit & integration tests
-   Swagger API documentation

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

| Parameter | Required | Required   | Description                     |
|-----------|----------|------------|---------------------------------|
| startDate | No       | yyyy-MM-dd | Start date of range             |
| endDate   | No       | yyyy-MM-dd | End date of range               |
| noOfMonth | No       | Integer    | Fetch rewards for last N months |

### Validation Rules

| Case                          	 | Result  	 |
|---------------------------------|-----------|
| noOfMonth + startDate/endDate 	 | Invalid 	 |
| only startDate                	 | Invalid 	 |
| only endDate                  	 | Invalid 	 |
| startDate > endDate           	 | Invalid 	 |
| noOfMonth ≤ 0                 	 | Invalid 	 |

------------------------------------------------------------------------

## 📜 Request Rules

### 1️⃣ Default Behavior
If no parameters are provided
```shell
GET /api/rewards/1
```
Returns rewards for last 3 months

### 2️⃣ Custom Month Range

```shell
GET /api/rewards/1?noOfMonth=6
```
Returns rewards for last 6 months
##### ⚠️ Cannot be used with startDate or endDate.

### 3️⃣ Custom Date Range
```shell
GET /api/rewards/1?startDate=2026-01-01&endDate=2026-03-01

```
Returns rewards within the specified range.

## 📄 Sample Response

```json
{
  "customerId": 1,
  "customerName": "Jatin Singh",
  "startDate": "2026-01-01",
  "endDate": "2026-03-01",
  "monthlyPoints": {
    "2026-01": 90,
    "2026-02": 40
  },
  "totalPoints": 130,
  "transactions": []
}
```
------------------------------------------------------------------------

## ❌ Error Response Format

```json
{
  "timestamp": "2026-03-03T10:42:27",
  "status": 404,
  "error": "Customer not found with id: 99",
  "path": "/api/rewards/99"
}
```
{ "timestamp": "2026-03-03T10:42:27", "status": 404, "error": "Customer
not found with id: 99", "path": "/api/rewards/99" }

------------------------------------------------------------------------

## 🧪 Running Tests

#### Run all tests
```shell
mvn clean test
```
#### Run tests with coverage
```shell
mvn clean verify
```

------------------------------------------------------------------------

## 🛠 Run Application

```shell
mvn spring-boot:run
```
Application runs at: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:rewardsdb

------------------------------------------------------------------------

## 🗄 H2 Database Console
```shell
http://localhost:8080/h2-console
```

## 📘 Swagger API Docs
```shell
http://localhost:8080/swagger-ui.html
```
OR
```shell
http://localhost:8080/swagger-ui/index.html
```

## 👨‍💻 Author

##### Jatin Pratap Singh
Full Stack Developer \
Java | Spring Boot | Vue.js | React | Microservices
