# Match Management API
This is a Spring Boot application that provides a RESTful Web API for managing sports matches and their associated betting odds. It allows CRUD operations on `Match` and `MatchOdds` entities via HTTP endpoints. The app is containerized with Docker and uses PostgreSQL for persistent storage.

---

## Technologies Used

- **Java 21**
- **Spring Boot 3.5.3**
    - Spring Web
    - Spring Data JPA
    - Hibernate Validator
- **PostgreSQL 16** (via Docker)
- **H2 Database** (for testing)
- **Lombok**
- **JUnit 5** & **Mockito** (for unit testing)
- **Maven** (build tool)
- **Docker** & **Docker Compose**

---

## Setup Instructions

### Run with Docker (Recommended)

> Make sure Docker is installed and running.

1. **Package the app locally**
   ```bash
   mvn clean package
   ```

2. **Start the containers (app + DB)**
   ```bash
   docker-compose up --build -d
   ```

3. **Useful Commands**
    - Restart app only: `docker-compose up -d app`
    - Stop app: `docker-compose stop app`
    - Stop DB: `docker-compose stop db`
    - Stop everything: `docker-compose down`
    - View logs: `docker-compose logs -f app`
    - Check running containers: `docker ps`

---

### Run Tests

```bash
mvn test
```

Unit tests use **H2 in-memory database** (configured via `application-test.properties`) for isolation.

---

## Application Structure

The project follows a layered architecture using clean separation of concerns:

```
com.example.matchmanagementapi
├── controller       # REST API layer (MatchController, MatchOddsController)
├── service          # Business logic (MatchService, MatchOddsService)
├── repository       # Spring Data JPA interfaces
├── domain           # JPA entities (Match, MatchOdds, Sport enum)
├── dto              # DTOs and manual mappers (MatchDTO, MatchOddsDTO, mappers)
├── exception        # Global exception handling (ControllerAdvice, custom exceptions)
```

---

## API Endpoints

### MatchController `http://localhost:8080/api/matches`

| Method | Endpoint             | Description                        |
|--------|----------------------|------------------------------------|
| GET**  | `/api/matches`       | Filter matches by query params     |
| GET    | `/api/matches/{id}`  | Get match by ID                    |
| GET    | `/api/matches/{id}/odds` | Get all MatchOdds for a given match |
| GET    | `/api/matches/count` | Get total count of matches         |
| POST   | `/api/matches`       | Save one or many matches           |
| DELETE | `/api/matches/{id}`  | Delete match by ID                 |
| DELETE | `/api/matches`       | Delete matches by list of IDs      |
| PUT    | `/api/matches/{id}`  | Update match fully                 |
| PATCH  | `/api/matches/{id}`  | Partially update match             |

### MatchOddsController `http://localhost:8080/api/matchOdds`

| Method | Endpoint                 | Description                          |
|--------|--------------------------|--------------------------------------|
| GET**  | `/api/matchOdds`         | Filter match odds by query params    |
| GET    | `/api/matchOdds/{id}`    | Get match odd by ID                  |
| GET    | `/api/matchOdds/count`   | Get total count of match odds        |
| POST   | `/api/matchOdds`         | Create one MatchOdds                 |
| POST   | `/api/matchOdds/batch`   | Create multiple MatchOdds entries    |
| DELETE | `/api/matchOdds/{id}`    | Delete match odd by ID               |
| DELETE | `/api/matchOdds`         | Delete match odds by list of IDs     |
| PUT    | `/api/matchOdds/{id}`    | Update MatchOdds fully               |
| PATCH  | `/api/matchOdds/{id}`    | Partially update MatchOdds           |

**[Extra Info on GET endpoints](docs/endpointsExtra.md)

---


### Postman Collection

A ready-to-use Postman collection is included in the project under the `postman/` directory.

- File: `match-management-api.postman_collection.json`
- It includes all supported endpoints with example requests and payloads.
- You can import it directly into Postman to test the API locally on `http://localhost:8080`.

To import:
1. Open Postman
2. Go to **File → Import**
3. Select the `.json` collection file from the `postman/` directory

---

## Design Considerations

- Layered architecture: controller → service → repository → domain
- DTOs and mappers ensure separation between API layer and entities
- Custom exception handling via `@ControllerAdvice` (`GlobalExceptionHandler`)
- Batch and single record processing supported for POST/DELETE
- Validation and filtering via query parameters
- Dockerized environment with PostgreSQL
- In-memory H2 used for isolated testing (application-test.properties)

### Functional Behavior & Business Logic Rules

- The `description` field of a `Match` is dynamically constructed using the `teamA` and `teamB` fields.
    - If the `description` is modified, the `teamA` and `teamB` values are updated accordingly.
    - If either `teamA` or `teamB` is modified, the `description` is automatically regenerated.
    - In case of a conflict during save or update (e.g., all three fields differ), the `description` is recomputed based on the team values.


- Duplicate prevention logic is enforced during Match and MatchOdds creation:

    - **Match**:
        - On `POST`, if a `Match` with the same `description`, `matchDate`, `matchTime`, `teamA`, `teamB`, and `sport` already exists, the request is rejected with HTTP 409 Conflict (for single inserts).
        - On batch insert (`POST /matches` with a list), such entries are silently skipped.
        - The `description` is always (re)generated as `teamA + "-" + teamB` before saving.

    - **MatchOdds**:
        - On `POST`, if a `MatchOdds` with the same `specifier`, `odd`, and associated `Match` already exists, the request is rejected with HTTP 409 Conflict (for single inserts).
        - On batch insert (`POST /matchOdds/batch`), duplicates are silently skipped.


- The `MatchOdds` entity has a many-to-one relationship with `Match`, meaning:
    - Each `MatchOdds` record is associated with one `Match`.
    - On insert or update, the system verifies that the referenced `Match` exists in the database.


- Relationship definition in the `MatchOdds` entity:

    - `@ManyToOne` defines the many-to-one relationship between `MatchOdds` and `Match`.
    - `cascade = MERGE` allows updates to propagate if the `Match` already exists.
    - `fetch = LAZY` ensures the associated `Match` is loaded only when needed.
    - `optional = false` and `nullable = false` make the association mandatory.

---

Author
----------
Christos Bampoulis  
GitHub: [@ChristosBaboulis](https://github.com/ChristosBaboulis)