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
