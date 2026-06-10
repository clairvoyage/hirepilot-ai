# HirePilot AI Backend

Spring Boot 4.x multi-module backend built with Maven and Java 25. The `shared` module contains common utilities, exceptions, constants, and response models used by the service modules.

Each service follows the same package structure:

- `controller`
- `service`
- `repository`
- `dto`
- `entity`
- `config`

Security is currently permissive so local health checks and Swagger UI stay available while authentication is being implemented.

## Requirements

- Java 25
- Maven 3.9 or newer

## Services

| Module | Port | Health | Swagger |
| --- | ---: | --- | --- |
| `auth` | `8081` | `GET /api/auth/health` | `http://localhost:8081/swagger-ui.html` |
| `candidate` | `8082` | `GET /api/candidate/health` | `http://localhost:8082/swagger-ui.html` |
| `employer` | `8083` | `GET /api/employer/health` | `http://localhost:8083/swagger-ui.html` |
| `jobs` | `8084` | `GET /api/jobs/health` | `http://localhost:8084/swagger-ui.html` |
| `ai` | `8085` | `GET /api/ai/health` | `http://localhost:8085/swagger-ui.html` |
| `search` | `8086` | `GET /api/search/health` | `http://localhost:8086/swagger-ui.html` |
| `integration` | `8087` | `GET /api/integration/health` | `http://localhost:8087/swagger-ui.html` |

Every service also exposes:

```text
GET /actuator/health
GET /v3/api-docs
```

## Build And Verify

Build all modules:

```bash
mvn clean package
```

Run the full quality gate:

```bash
mvn verify
```

This checks compilation, tests, formatting, Checkstyle, PMD, SpotBugs, and Java/Maven versions.

Fix formatting issues:

```bash
mvn spotless:apply
```

## Run A Service

Run a service from the repository root:

```bash
mvn -pl auth -am spring-boot:run
```

Run a packaged service:

```bash
java -jar auth/target/auth-0.0.1-SNAPSHOT.jar
```

Swap `auth` for any other module name. If `java -jar` fails with `UnsupportedClassVersionError`, confirm your shell is using Java 25:

```bash
java -version
```
