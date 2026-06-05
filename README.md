# HirePilot AI Backend

Spring Boot multi-module backend using Maven. Each service module follows the same package structure:

- `controller`
- `service`
- `repository`
- `dto`
- `entity`
- `config`

The `shared` module contains common utilities, exceptions, constants, and response models used by all service modules.

The project targets Java 25 and uses Spring Boot 4.x.

Each service module includes Spring Web, Validation, Actuator, Spring Security, and Swagger UI. Security is currently configured as a permissive placeholder so local health checks and Swagger stay accessible while authentication is being implemented.

## Prerequisites

- Java 25
- Maven 3.9 or newer

## Modules

| Module | Default Port | Health Endpoint |
| --- | ---: | --- |
| `auth` | `8081` | `GET /api/auth/health` |
| `candidate` | `8082` | `GET /api/candidate/health` |
| `employer` | `8083` | `GET /api/employer/health` |
| `jobs` | `8084` | `GET /api/jobs/health` |
| `ai` | `8085` | `GET /api/ai/health` |
| `search` | `8086` | `GET /api/search/health` |
| `integration` | `8087` | `GET /api/integration/health` |

Each service also exposes Spring Boot Actuator health:

```text
GET /actuator/health
```

## Build

Build every module from the repository root:

```bash
mvn clean package
```

Build one service and its required dependencies:

```bash
mvn -pl auth -am package
```

## Code Quality

Run the full local quality gate:

```bash
mvn verify
```

This runs compilation, tests, formatting checks, Checkstyle linting, PMD static analysis, SpotBugs bytecode analysis, and Java/Maven version enforcement.

Fix automatic formatting issues:

```bash
mvn spotless:apply
```

Then rerun:

```bash
mvn verify
```

## Run

Run a service from the repository root:

```bash
mvn -pl auth -am spring-boot:run
```

The `-am` flag tells Maven to also build required modules, such as `shared`.

If you are already inside a service directory such as `auth/`, go back to the project root first:

```bash
cd ..
mvn -pl auth -am spring-boot:run
```

Run a packaged service:

```bash
java -jar auth/target/auth-0.0.1-SNAPSHOT.jar
```

Swap `auth` for any other service module name.

This project targets Java 25. If `java -jar` fails with `UnsupportedClassVersionError`, your terminal is using an older Java runtime. Check it with:

```bash
java -version
```

Install Java 25 with Homebrew:

```bash
brew install openjdk@25
```

Make Java 25 the default for new terminal sessions by adding this to `~/.zshrc`:

```bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home"
export PATH="/opt/homebrew/opt/openjdk@25/bin:$PATH"
```

Reload your shell and confirm Java 25 is active:

```bash
source ~/.zshrc
java -version
```

Then run the packaged service:

```bash
java -jar auth/target/auth-0.0.1-SNAPSHOT.jar
```

## Docker

Build and run all services with Docker Compose:

```bash
docker compose up --build
```

Run just one service:

```bash
docker compose up --build auth
```

The Compose stack exposes the same ports used by the local Maven workflow:

| Service | URL |
| --- | --- |
| `auth` | `http://localhost:8081` |
| `candidate` | `http://localhost:8082` |
| `employer` | `http://localhost:8083` |
| `jobs` | `http://localhost:8084` |
| `ai` | `http://localhost:8085` |
| `search` | `http://localhost:8086` |
| `integration` | `http://localhost:8087` |

Check a running service:

```bash
curl http://localhost:8081/actuator/health
curl http://localhost:8081/api/auth/health
```

Stop the stack:

```bash
docker compose down
```

You can also build and run a single image directly with Docker:

```bash
docker build \
  --build-arg SERVICE=auth \
  --build-arg SERVICE_PORT=8081 \
  -t hirepilot/auth:local .

docker run --rm -p 8081:8081 hirepilot/auth:local
```

Swap `auth` and `8081` for any other service module and port.

## PR Preview Deployments

Pull requests from branches in this repository deploy a preview stack after the Maven quality gate and Docker image builds pass. The preview workflow posts a PR comment and sets a GitHub Environment URL using this pattern:

```text
https://pr-<pull-request-number>.<PREVIEW_BASE_DOMAIN>
```

Preview deployments use the non-production Spring `preview` profile. The preview profile files are named `application-preview.properties` in each service module.

The preview host is expected to run Docker, Docker Compose, and Traefik. Create the external Traefik network once on that host:

```bash
docker network create preview-proxy
```

Configure wildcard DNS so `*.PREVIEW_BASE_DOMAIN` points at the preview host, then add these GitHub repository secrets:

| Secret | Purpose |
| --- | --- |
| `PREVIEW_SSH_HOST` | Preview host DNS name or IP address |
| `PREVIEW_SSH_USER` | SSH user that can run Docker Compose |
| `PREVIEW_SSH_KEY` | Private SSH key for the preview host |

Add this required GitHub repository variable:

| Variable | Example |
| --- | --- |
| `PREVIEW_BASE_DOMAIN` | `preview.example.com` |

Optional repository variables:

| Variable | Default |
| --- | --- |
| `PREVIEW_REMOTE_ROOT` | `hirepilot-previews` |
| `PREVIEW_SCHEME` | `https` |
| `PREVIEW_TRAEFIK_CERT_RESOLVER` | `letsencrypt` |
| `PREVIEW_TRAEFIK_ENTRYPOINT` | `websecure` |
| `PREVIEW_TRAEFIK_NETWORK` | `preview-proxy` |

The preview URL routes service API paths to the matching container:

| Path Prefix | Service |
| --- | --- |
| `/api/auth` | `auth` |
| `/api/candidate` | `candidate` |
| `/api/employer` | `employer` |
| `/api/jobs` | `jobs` |
| `/api/ai` | `ai` |
| `/api/search` | `search` |
| `/api/integration` | `integration` |

## Swagger UI

After a service is running, open its Swagger UI in a browser:

```text
http://localhost:8081/swagger-ui.html
```

For the `auth` service, expand `health-controller`, run `GET /api/auth/health`, and click **Execute**. A working service returns a `200` response with `success: true` and `status: "UP"`.

You can also check the generated OpenAPI JSON directly:

```bash
curl http://localhost:8081/v3/api-docs
```

Use the matching port for other services:

| Module | Swagger UI |
| --- | --- |
| `auth` | `http://localhost:8081/swagger-ui.html` |
| `candidate` | `http://localhost:8082/swagger-ui.html` |
| `employer` | `http://localhost:8083/swagger-ui.html` |
| `jobs` | `http://localhost:8084/swagger-ui.html` |
| `ai` | `http://localhost:8085/swagger-ui.html` |
| `search` | `http://localhost:8086/swagger-ui.html` |
| `integration` | `http://localhost:8087/swagger-ui.html` |
