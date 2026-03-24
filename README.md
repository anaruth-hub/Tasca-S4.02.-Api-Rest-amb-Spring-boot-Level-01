# Fruit API H2

## Description
Fruit API H2 is a RESTful backend application built with Spring Boot to manage fruit stock in a grocery inventory system.

This project implements a complete CRUD (Create, Read, Update, Delete) API for fruits, using an H2 in-memory database for persistence during development and testing.

## Exercise Statement
This project is part of the **Sprint 4.02 - REST API with Spring Boot** assignment.

The goal of Level 1 is to develop a REST API that allows users to manage fruit stock entries. Each fruit contains:
- a unique identifier
- a name
- a weight in kilos

The application must support:
- creating a fruit
- retrieving all fruits
- retrieving one fruit by id
- updating a fruit
- deleting a fruit

## Features
- Create a new fruit
- Retrieve all fruits
- Retrieve a fruit by id
- Update an existing fruit
- Delete a fruit
- Input validation with Bean Validation
- Global exception handling
- H2 in-memory database integration
- Layered architecture following MVC principles
- DTO pattern to avoid exposing entities directly
- Controller tests with MockMvc
- Service tests with Mockito
- Docker-ready structure

## Technologies
- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- JUnit 5
- Mockito
- MockMvc
- Maven
- Docker

## Project Structure
```text
src
├── main
│   ├── java/cat/itacademy/s04/t02/n01
│   │   ├── controllers
│   │   ├── dto
│   │   ├── exception
│   │   ├── mapper
│   │   ├── model
│   │   ├── repository
│   │   ├── services
│   │   └── FruitApiH2Application.java
│   └── resources
│       └── application.properties
└── test
    └── java/cat/itacademy/s04/t02/n01
        ├── controllers
        └── services
API Endpoints
Create fruit
POST /fruits
Get all fruits
GET /fruits
Get fruit by id
GET /fruits/{id}
Update fruit
PUT /fruits/{id}
Delete fruit
DELETE /fruits/{id}
Request Example
POST /fruits
{
  "name": "Apple",
  "weightInKilos": 10
}
Response Example
{
  "id": 1,
  "name": "Apple",
  "weightInKilos": 10
}
Validation Rules
name must not be blank
weightInKilos must not be null
weightInKilos must be greater than zero
Error Handling

The application uses a global exception handler to provide consistent error responses.

Examples:

400 Bad Request for invalid input
404 Not Found when a fruit does not exist
500 Internal Server Error for unexpected failures
H2 Database

This project uses an H2 in-memory database.

H2 console:

http://localhost:8080/h2-console

Default configuration:

JDBC URL: jdbc:h2:mem:fruitdb
Username: sa
Password: (empty)
Installation and Execution
1. Clone the repository
git clone <repository-url>
2. Enter the project folder
cd fruit-api-h2
3. Run the application
./mvnw spring-boot:run

On Windows PowerShell:

.\mvnw.cmd spring-boot:run
Running Tests

Run all tests with:

.\mvnw test
Design Decisions
DTO pattern was used to separate API contracts from persistence entities.
Service layer was implemented to isolate business logic from controllers.
Global exception handling was added to centralize error management.
Bean Validation was used to validate input data at the API boundary.
Mapper class was created to avoid repetitive conversion logic.
H2 was chosen for Level 1 because it is simple and fast for development and testing.
Future Improvements
Add integration tests for repository layer
Add API documentation with Swagger/OpenAPI
Add Dockerfile for containerized execution
Add CI pipeline for automated testing
Author

Ana Ruth