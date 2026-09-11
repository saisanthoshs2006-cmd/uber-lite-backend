# RideFlow Backend

RideFlow is a production-minded ride-booking backend built with Java 25 and Spring Boot 4.1.1. It demonstrates a complete ride lifecycle with persistence, validation, migration management, and integration tests.

## Features

- Create and list ride requests
- Retrieve a ride by ID
- Assign a driver to a requested ride
- Enforce ride state transitions in the domain model
- Validate request payloads and return consistent API errors
- Persist rides with PostgreSQL and Flyway migrations
- Run integration tests against an isolated H2 database
- Expose health information through Spring Boot Actuator

## API

### Create a ride

```bash
curl -X POST http://localhost:8080/api/rides \
  -H 'Content-Type: application/json' \
  -d '{
    "passengerId": "passenger-1",
    "pickupLocation": "Koramangala",
    "dropoffLocation": "Indiranagar",
    "estimatedFare": 245.50
  }'
```

### Assign a driver

```bash
curl -X PATCH http://localhost:8080/api/rides/{rideId}/driver \
  -H 'Content-Type: application/json' \
  -d '{"driverId":"driver-7"}'
```

### Change ride status

```bash
curl -X PATCH http://localhost:8080/api/rides/{rideId}/status \
  -H 'Content-Type: application/json' \
  -d '{"status":"IN_PROGRESS"}'
```

Valid lifecycle transitions are `REQUESTED -> ACCEPTED -> IN_PROGRESS -> COMPLETED`. A ride can be cancelled before completion.

## Run Locally

Requirements: Java 25 and Docker with Docker Compose.

Start PostgreSQL:

```bash
docker compose up -d postgres
```

Run the application:

```bash
./mvnw spring-boot:run
```

Run the test suite:

```bash
./mvnw clean verify
```

The default local database is `rideflow` with username `postgres` and password `postgres`. Override them with `DATABASE_URL`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD`.

## Architecture

The code is organized by feature rather than technical layer:

- `ride`: ride aggregate, lifecycle rules, persistence, service, DTOs, and REST controller
- `common`: shared REST error handling
- `db/migration`: versioned Flyway database schema

## Resume Description

Built a ride-booking backend with Java 25, Spring Boot, PostgreSQL, JPA, and Flyway, implementing validated ride creation, driver assignment, lifecycle state transitions, structured API errors, and integration tests.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).
