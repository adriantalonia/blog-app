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
| `spring.application.name` | The name of the application | `blog-app` |
| `TODO` | Database connection details (JDBC URL, username, password) | `None (Configure in application.yaml)` |

> **Note**: Currently, `application.yaml` only defines the application name. Database credentials and other environment-specific configurations should be added to this file or passed via environment variables.

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
│   │   │       └── BlogAppApplication.java  # Application entry point
│   │   └── resources/
│   │       └── application.yaml             # Main configuration file
│   └── test/                                # Automated tests
├── pom.xml                                  # Maven project descriptor
├── mvnw                                     # Maven wrapper (UNIX)
├── mvnw.cmd                                 # Maven wrapper (Windows)
├── docker-compose.yml                       # Docker configuration (Currently empty)
└── README.md                                # This file
```

## License
[TODO: Add license information, e.g., MIT, Apache-2.0]
