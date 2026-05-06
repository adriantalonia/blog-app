# blog-app

## Overview
`blog-app` is a Java-based application built with **Spring Boot**, designed for managing blog posts. It leverages **Spring Data JPA** for data persistence and **Spring Web (MVC)** for its web interface.

## Tech Stack
- **Language**: Java 23
- **Framework**: Spring Boot 4.0.5
- **Build Tool**: Apache Maven
- **Database**: PostgreSQL (Runtime), H2 (Test)
- **Other libraries**: Lombok, Spring Validation, Spring Security, JJWT (JSON Web Token)

## Requirements
To build and run this application, you will need:
- **Java Development Kit (JDK) 23** or higher.
- **Maven** 3.x (optional, as the project includes the Maven Wrapper `./mvnw`).
- A running **PostgreSQL** instance (if configured for runtime usage).

## Setup & Run Commands

### Clone the repository
```bash
git clone <repository-url>
cd blog-app
```

### Build the application
Using the Maven wrapper:
```bash
./mvnw clean install
```

### Run the application
```bash
./mvnw spring-boot:run
```
Alternatively, run the JAR file after building:
```bash
java -jar target/blog-app-0.0.1-SNAPSHOT.jar
```

## Scripts
- `./mvnw`: Maven wrapper script for Linux/macOS.
- `mvnw.cmd`: Maven wrapper script for Windows.

## Configuration & Environment Variables
The application configuration is managed in `src/main/resources/application.yaml`.

| Variable / Property | Description | Default Value |
|---------------------|-------------|---------------|
| `APP_PROFILE` | Spring application profile | `dev` |
| `SERVER_PORT` | Port for the web server | `8080` |
| `DB_HOST` | Database host | `localhost` |
| `DB_PORT` | Database port | `5433` |
| `DB_NAME` | Database name | `blog_db` |
| `DB_USERNAME` | Database user | `postgres` |
| `DB_PASSWORD` | Database password | `postgres` |
| `JPA_DDL_AUTO` | Hibernate DDL generation strategy | `update` |
| `JPA_SHOW_SQL` | Log SQL queries | `false` |
| `JWT_SECRET` | Secret key for JWT signing | `1q2w3e4r5t6y7u8i9o0pqawsedrftgyhujikolpazsxdcfvgbhnjmkl` |
| `JWT_EXPIRATION_MS` | Access token expiration (ms) | `900000` (15 min) |
| `JWT_REFRESH_EXPIRATION_MS` | Refresh token expiration (ms) | `604800000` (7 days) |

> **Note**: Database credentials and other environment-specific configurations are pre-configured for a local development environment. You can override them using environment variables or by modifying `application.yaml`.

## Tests
The project includes automated tests using JUnit and Spring Boot's testing support.

### Run all tests
```bash
./mvnw test
```
The test suite includes:
- Unit tests
- Integration tests (using H2 in-memory database)

## Project Structure
```text
blog-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/atrdev/blogapp/
│   │   │       ├── config/              # Configuration classes (JPA, Security, etc.)
│   │   │       ├── controller/          # REST Controllers
│   │   │       ├── dto/                 # Data Transfer Objects
│   │   │       ├── entity/              # JPA Data Entities (User, Post, Category, Tag)
│   │   │       ├── enums/               # Enums (PostStatus, etc.)
│   │   │       ├── mapper/              # MapStruct mappers
│   │   │       ├── repository/          # Spring Data JPA Repositories
│   │   │       ├── security/            # Security-related classes (UserDetails, etc.)
│   │   │       ├── service/             # Service layer interfaces and implementations
│   │   │       └── BlogAppApplication.java  # Application entry point
│   │   └── resources/
│   │       └── application.yaml             # Main configuration file
│   └── test/                                # Automated tests
├── pom.xml                                  # Maven project descriptor
├── mvnw                                     # Maven wrapper (UNIX)
├── mvnw.cmd                                 # Maven wrapper (Windows)
├── docker-compose.yml                       # Docker configuration for infrastructure
└── README.md                                # This file
```

## Core Entities
The application core logic revolves around these main entities:
- **User**: Represents authors and registered users.
- **Post**: Represents blog content, authored by a User, belongs to a Category, and can have multiple Tags.
- **Category**: Used to group Posts into high-level topics.
- **Tag**: Keywords for classifying and searching Posts.

### Relationships
- **Post** → **User**: Many-to-One (Author)
- **Post** → **Category**: Many-to-One
- **Post** ↔ **Tag**: Many-to-Many
- **Category** → **Post**: One-to-Many
- **Tag** → **Post**: Many-to-Many (Bidirectional)

## API Endpoints

### Authentication
- `POST /api/v1/auth`: Authenticate a user and return a JWT token.

### Categories
- `GET /api/v1/categories`: List all categories. Includes the `postCount` (number of published posts) for each category.
- `POST /api/v1/categories`: Create a new category.
- `DELETE /api/v1/categories/{id}`: Delete a category by ID. (Note: Only categories with no associated posts can be deleted).

## Security
The application is secured using **Spring Security** with **JWT-based stateless authentication**.

- **Authentication**: A `POST /api/v1/auth` request with valid credentials returns a JWT token in the `Authorization` header (Bearer token).
- **Public Endpoints**: `GET` requests to `/api/v1/posts/**`, `/api/v1/categories/**`, and `/api/v1/tags/**` are permitted without authentication.
- **Protected Endpoints**: All other requests (e.g., creating, updating, or deleting resources) require a valid authentication token.
- **User Management**: Uses a custom `UserDetailsService` that retrieves user information from the database via the `UserRepository`.
- **Development Test User**: For development purposes, a test user is automatically created if it doesn't exist:
  - **Email**: `user@test.com`
  - **Password**: `password`

## Error Handling
The application uses **RFC 7807 Problem Details** for error responses. 
- **Authentication Failure**: Returned when invalid credentials are provided (Status 401).
- **Resource Not Found**: Returned when a requested entity does not exist (Status 404).
- **Conflict**: Returned when a business rule is violated, such as deleting a category with existing posts (Status 409).
- **Internal Server Error**: For any other unhandled exceptions (Status 500).

## License
[TODO: Add license information, e.g., MIT, Apache-2.0]
