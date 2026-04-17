# blog-app

## Overview
`blog-app` is a Java-based application built with **Spring Boot**, designed for managing blog posts. It leverages **Spring Data JPA** for data persistence and **Spring Web (MVC)** for its web interface.

## Tech Stack
- **Language**: Java 23
- **Framework**: Spring Boot 4.0.5
- **Build Tool**: Apache Maven
- **Database**: PostgreSQL (Runtime), H2 (Test)
- **Other libraries**: Lombok, Spring Validation

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

### Categories
- `GET /api/v1/categories`: List all categories. Includes the `postCount` (number of published posts) for each category.

## License
[TODO: Add license information, e.g., MIT, Apache-2.0]
