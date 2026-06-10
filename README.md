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

## Docker

Build and run all services with Docker Compose:

```bash
docker compose up --build
```

Run one service:

```bash
docker compose up --build auth
```

Stop the stack:

```bash
docker compose down
```

## PR Preview Deployments

Pull requests from branches in this repository can deploy a Docker Compose preview stack after `mvn verify` passes and preview images are pushed to GitHub Container Registry.

The preview host needs Docker, Docker Compose, and Traefik. Create the external Traefik network once:

```bash
docker network create preview-proxy
```

Configure wildcard DNS so `*.PREVIEW_BASE_DOMAIN` points at the preview host, then set these GitHub repository secrets:

| Secret | Purpose |
| --- | --- |
| `PREVIEW_SSH_HOST` | Preview host DNS name or IP address |
| `PREVIEW_SSH_USER` | SSH user that can run Docker Compose |
| `PREVIEW_SSH_KEY` | Private SSH key for the preview host |

Set this repository variable:

| Variable | Example |
| --- | --- |
| `PREVIEW_BASE_DOMAIN` | `preview.example.com` |

Optional variables:

| Variable | Default |
| --- | --- |
| `PREVIEW_REMOTE_ROOT` | `hirepilot-previews` |
| `PREVIEW_SCHEME` | `https` |
| `PREVIEW_TRAEFIK_CERT_RESOLVER` | `letsencrypt` |
| `PREVIEW_TRAEFIK_ENTRYPOINT` | `websecure` |
| `PREVIEW_TRAEFIK_NETWORK` | `preview-proxy` |

Preview URLs use this pattern:

```text
https://pr-<pull-request-number>.<PREVIEW_BASE_DOMAIN>
```
