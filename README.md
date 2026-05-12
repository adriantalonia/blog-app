# blog-app

## Overview
`blog-app` is a Java-based application built with **Spring Boot**, designed for managing blog posts. It leverages **Spring Data JPA** for data persistence and **Spring Web (MVC)** for its web interface.

## Tech Stack
- **Language**: Java 23
- **Framework**: Spring Boot 3.4.1 (Latest)
- **Build Tool**: Apache Maven
- **Database**: PostgreSQL 17 (Containerized), H2 (In-memory for tests)
- **Security**: Spring Security 6.x, JJWT (JSON Web Token)
- **Object Mapping**: MapStruct 1.6.3
- **Infrastructure**: Docker & Docker Compose
- **Other libraries**: Lombok, Spring Validation, Hibernate (JPA)

## Architecture & Design
The application follows a standard N-Tier architecture:
- **Presentation Layer**: REST Controllers handling HTTP requests/responses and using DTOs for data transfer.
- **Service Layer**: Contains core business logic, transactional management, and coordinates between repositories and mappers.
- **Data Access Layer**: Spring Data JPA Repositories for database interaction.
- **Cross-cutting Concerns**: Global exception handling, JWT-based security, and MapStruct for entity-DTO mapping.

### Key Business Logic
- **Post Reading Time**: Automatically calculated whenever a post is created or updated. It uses a base rate of 200 words per minute.
- **Post Status Workflow**: Supports `DRAFT` and `PUBLISHED` statuses. Only `PUBLISHED` posts are visible via the public API, while `DRAFT` posts are reserved for the author.
- **Dynamic Filtering**: Posts can be filtered by Category, Tag, or both simultaneously using JPA-backed query methods.
- **Data Integrity**: Categories and Tags cannot be deleted if they are associated with any posts, ensuring referential integrity at the application level.

## Database Schema & Relationships
The project uses a relational schema with the following key relationships:
- **One-to-Many**: `Category` → `Post` (A category can have many posts).
- **Many-to-Many**: `Post` ↔ `Tag` (Posts can have multiple tags, and tags can be shared across posts).
- **Many-to-One**: `Post` → `User` (Each post is authored by a single user).

### Query Optimization
- **Post Counts**: Category and Tag listings include a `postCount` field. This is optimized using `JOIN FETCH` or `@EntityGraph` in repositories to avoid N+1 select problems.

## Infrastructure & Docker
The project includes a `docker-compose.yml` file to quickly spin up the required infrastructure.

### Services Included:
- **PostgreSQL**: The primary database, accessible on port `5433`.
- **Adminer**: A lightweight database management tool accessible at [http://localhost:8888](http://localhost:8888).

To start the infrastructure:
```bash
docker-compose up -d
```

## Security Implementation
The application implements a stateless authentication flow using **JWT (JSON Web Tokens)**:
1. **Authentication**: User submits credentials to `/api/v1/auth`.
2. **Token Generation**: Upon success, the server generates a signed JWT.
3. **Authorization**: For protected routes, the client must include the token in the `Authorization: Bearer <token>` header.
4. **Validation**: The `JwtAuthenticationFilter` interceptor validates the token on every request.

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

### Tags
- `GET /api/v1/tags`: List all tags. Includes the `postCount` for each tag.
- `POST /api/v1/tags`: Bulk create new tags. Accepts a list of tag names.
- `DELETE /api/v1/tags/{id}`: Delete a tag by ID. (Note: Only tags with no associated posts can be deleted).

### Posts
- `GET /api/v1/posts`: List all published posts. 
  - **Optional Query Parameters**:
    - `categoryId`: Filter posts by category ID.
    - `tagId`: Filter posts by tag ID.
- `GET /api/v1/posts/{id}`: Get a specific post by ID.
- `GET /api/v1/posts/drafts`: List all draft posts for the authenticated user.
- `POST /api/v1/posts`: Create a new post. Automatically calculates reading time based on content.
- `PUT /api/v1/posts/{id}`: Update an existing post.
- `DELETE /api/v1/posts/{id}`: Delete a post by ID.

## Security
The application is secured using **Spring Security** with **JWT-based stateless authentication**.

- **Authentication**: A `POST /api/v1/auth` request with valid credentials returns a JWT token in the `Authorization` header (Bearer token).
- **Public Endpoints**: `GET` requests to `/api/v1/posts` (excluding drafts), `/api/v1/posts/{id}`, `/api/v1/categories/**`, and `/api/v1/tags/**` are permitted without authentication.
- **Protected Endpoints**: All other requests (e.g., creating, updating, or deleting resources, and accessing drafts) require a valid authentication token.
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
