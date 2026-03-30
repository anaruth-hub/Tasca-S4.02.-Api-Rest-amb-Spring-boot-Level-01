<div align="center">

# 🍎 Sprint 4.02 — REST APIs with Spring Boot

### Multi-module project with H2, MySQL and MongoDB

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.12-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue)
![H2](https://img.shields.io/badge/H2-InMemory-lightgrey)
![MySQL](https://img.shields.io/badge/MySQL-8.4-4479A1)
![MongoDB](https://img.shields.io/badge/MongoDB-7-47A248)
![Tests](https://img.shields.io/badge/Tests-JUnit%205%20%7C%20MockMvc-blueviolet)

</div>

---

## 📌 Project Overview

This repository contains the complete solution for **Sprint 4.02 – REST API with Spring Boot**, structured as a **multi-module Maven project**.

The goal of this sprint is to build **three independent Spring Boot applications**, each one exposing a complete CRUD REST API and using a different persistence technology:

- **Level 1:** H2 in-memory database
- **Level 2:** MySQL with relational entities
- **Level 3:** MongoDB with embedded documents

The project follows clean architecture principles and applies:

- DTO pattern
- MVC structure
- input validation
- global exception handling
- automated testing
- Docker support for development

---

## 🎯 Sprint Goals

Through these three levels, the project demonstrates how to:

- build REST APIs with Spring Boot
- apply HTTP verbs correctly (`GET`, `POST`, `PUT`, `DELETE`)
- use `ResponseEntity` and proper HTTP status codes
- validate request bodies with Bean Validation
- avoid exposing persistence entities directly
- manage exceptions through a centralized `GlobalExceptionHandler`
- implement automated tests using `MockMvc`, JUnit 5 and Mockito
- work with SQL and NoSQL persistence strategies
- organize a backend project using scalable and maintainable structure

---

## 🧩 Modules

| Module | Level | Database | Main Domain |
|--------|------|----------|-------------|
| `fruit-api-h2` | Level 1 | H2 | Fruit stock |
| `fruit-api-mysql` | Level 2 | MySQL | Fruits and providers |
| `fruit-order-api-mongo` | Level 3 | MongoDB | Fruit orders |

---

## 📁 Multi-Module Structure

```text
fruit-api-parent/
├── .mvn/
├── fruit-api-h2/
│   ├── src/
│   ├── docs/
│   ├── Dockerfile
│   └── README.md
├── fruit-api-mysql/
│   ├── src/
│   ├── docs/
│   └── README.md
├── fruit-order-api-mongo/
│   ├── src/
│   ├── docs/
│   └── README.md
├── docker-compose.yml
├── pom.xml
└── README.md

🛠 Technologies Used
Backend
Java 21
Spring Boot 3.5.12
Spring Web
Spring Data JPA
Spring Data MongoDB
Bean Validation
Databases
H2 Database
MySQL 8.4
MongoDB 7
Testing
JUnit 5
MockMvc
Mockito
Build & Dev Tools
Maven Wrapper
Docker
Docker Compose
IntelliJ IDEA
Postman
🧱 Design and Good Practices

This project was implemented following these principles:

KISS → simple and readable solutions
DRY → avoid duplicated logic
SRP / SOLID mindset → focused classes and methods
DTO pattern → controllers do not expose entities directly
Explicit validation → user input is always validated
Global exception handling → centralized and consistent API errors
Clear package structure → easier maintenance and scalability
🚀 How to Run the Project

This repository is a parent Maven project.
Each level is an independent Spring Boot application and must be run separately.

Level 1 — H2 API
Description

REST API for managing fruit stock using an H2 in-memory database.

Main entity
Fruit
id
name
weightInKilos
Run

From the parent root:

mvn -pl fruit-api-h2 spring-boot:run
Base URL
http://localhost:8080
Main endpoints
Method	Endpoint	Description
POST	/fruits	Create fruit
GET	/fruits	Get all fruits
GET	/fruits/{id}	Get fruit by id
PUT	/fruits/{id}	Update fruit
DELETE	/fruits/{id}	Delete fruit
Level 2 — MySQL API
Description

REST API for managing fruits and providers using MySQL and a relational model.

Main entities
Provider
id
name
country
Fruit
id
name
weightInKilos
provider
Relationship
Fruit → @ManyToOne → Provider
Start MySQL with Docker

From the parent root:

docker compose up -d
Run

From the parent root:

mvn -pl fruit-api-mysql spring-boot:run
Base URL
http://localhost:8082
Main endpoints
Providers
Method	Endpoint	Description
POST	/providers	Create provider
GET	/providers	Get all providers
PUT	/providers/{id}	Update provider
DELETE	/providers/{id}	Delete provider
Fruits
Method	Endpoint	Description
POST	/fruits	Create fruit
GET	/fruits	Get all fruits
GET	/fruits/{id}	Get fruit by id
PUT	/fruits/{id}	Update fruit
DELETE	/fruits/{id}	Delete fruit
GET	/fruits?providerId={id}	Filter fruits by provider
Business rule

A provider cannot be deleted if it has associated fruits.
In that case, the API returns:

400 Bad Request
Level 3 — MongoDB API
Description

REST API for managing fruit orders using MongoDB and embedded documents.

Main document
Order
id
clientName
deliveryDate
items
Embedded document: OrderItem
fruitName
quantityInKilos
MongoDB

MongoDB must be available locally on:

localhost:27017
Run

From the parent root:

mvn -pl fruit-order-api-mongo spring-boot:run
Base URL
http://localhost:8083
Main endpoints
Method	Endpoint	Description
POST	/orders	Create order
GET	/orders	Get all orders
GET	/orders/{id}	Get order by id
PUT	/orders/{id}	Update order
DELETE	/orders/{id}	Delete order
Business rule

The deliveryDate must be at least tomorrow.
If the date is today or earlier, the API returns:

400 Bad Request
✅ Example Requests
Level 1 — Create fruit
{
  "name": "Apple",
  "weightInKilos": 10
}
Level 2 — Create provider
{
  "name": "Fresh Fruits Ltd",
  "country": "Spain"
}
Level 2 — Create fruit with provider
{
  "name": "Banana",
  "weightInKilos": 20,
  "providerId": 1
}
Level 3 — Create order
{
  "clientName": "Ana",
  "deliveryDate": "2026-03-30",
  "items": [
    {
      "fruitName": "Apple",
      "quantityInKilos": 5
    },
    {
      "fruitName": "Banana",
      "quantityInKilos": 3
    }
  ]
}
🧪 Automated Tests

This project includes automated tests for controller and service layers.

Run all tests
mvn test
Run tests by module
Level 1
mvn -pl fruit-api-h2 test
Level 2
mvn -pl fruit-api-mysql test
Level 3
mvn -pl fruit-order-api-mongo test
📷 API Evidence / Screenshots

Each module may include screenshots inside its own docs/screenshots folder.

Suggested examples
application running
successful POST request
GET list request
successful PUT request
successful DELETE request
validation errors
business rule errors
📘 Individual Module Documentation

Each module also includes its own README:

fruit-api-h2/README.md
fruit-api-mysql/README.md
fruit-order-api-mongo/README.md

These files provide module-specific instructions and examples.

👩‍💻 Author

Ana Ruth

Bootcamp project for Java & Spring Framework 2025–2026


---
