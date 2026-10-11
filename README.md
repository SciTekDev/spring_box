# spring_box

Sandbox multi-module project for experimenting with a small set of Spring Boot APIs that
register themselves with [Consul](https://www.consul.io/) and ship logs to an ELK stack via Log4j2.

## Modules

| Module | Port | Purpose |
|---|---|---|
| `device-status` | 8081 | Simulated device status API — get/track status of remote equipment (embedded devices, Linux/Windows servers). |
| `data-store` | 8082 | Simulated data warehouse/storage API — tracks device IDs, types, and other data sets. |
| `device-control` | 8083 | Device control API — on/off power, LED control, ROS2-style (`cmd_vel`) movement commands. |
| `device-tests` | n/a | JUnit 5 integration test project that calls the three APIs over HTTP. |

## Prerequisites

- Java 21 (JDK)
- Maven 3.9+
- Docker + Docker Compose (for running Consul and, later, ELK locally)

Run the prerequisite checker before you start:

```powershell
# Windows
powershell -ExecutionPolicy Bypass -File scripts/check-prereqs.ps1
```

```bash
# macOS/Linux
bash scripts/check-prereqs.sh
```

## Running infrastructure (Consul) and the services

Consul now lives in the [my_elk_stack](../my_elk_stack) compose project and is shared with this
project over an external Docker network (`lab-network`). Start that project first so the network
and Consul container exist:

```bash
cd ../my_elk_stack
docker compose up -d
```

Then, from this repo:

```bash
docker compose up -d --build
```

This builds and starts all three services (`device-status`, `data-store`, `device-control`), each
pointed at the `consul` container (reachable over `lab-network`) via `SPRING_CLOUD_CONSUL_HOST`.
Consul UI is available at http://localhost:8500.

## Building

From the repo root (parent aggregator POM):

```bash
mvn clean install
```

## Building and running unit tests

Each of the three service modules (`device-status`, `data-store`, `device-control`) has its own
JUnit 5 unit tests under `src/test/java`, exercising controller logic directly (no running server,
no Consul, no Docker required). These run automatically as part of the normal build:

```bash
mvn clean install
```

The `device-tests` module is excluded from this — it contains integration tests only, and is not
part of `docker compose up` since it doesn't run a service of its own (see below).

## Running a service

```bash
mvn -pl device-status spring-boot:run
mvn -pl data-store spring-boot:run
mvn -pl device-control spring-boot:run
```

Each service registers itself with Consul on startup (see `application.yml` in each module) and
logs startup, request handling, and exceptions via Log4j2 (`log4j2.xml` in each module's
`src/main/resources`). The Log4j2 config includes a console appender plus a TCP socket appender
pointed at `localhost:5000`, matching a typical Logstash TCP input — update the host/port once the
ELK stack is stood up.

## Running integration tests (device-tests)

The `device-tests` module contains JUnit 5 integration tests (`*IT.java`) that call each API's
HTTP endpoints directly over the network. They are a separate project from the three services and
are **not** part of `docker compose up` — they assume the services are already running (either via
`docker compose up -d --build` or `mvn spring-boot:run`) and are reached from outside.

Integration tests are skipped by default (`skipITs=true`) so they never run as part of a normal
`mvn clean install` or CI build. To run them explicitly once the services are up:

```bash
mvn -pl device-tests -am verify -DskipITs=false
```

Override base URLs via `DEVICE_STATUS_URL`, `DATA_STORE_URL`, `DEVICE_CONTROL_URL` environment
variables if services aren't on their default localhost ports.


## Project layout

```
spring_box/
├── pom.xml                  # parent aggregator POM
├── docker-compose.yml       # device-status/data-store/device-control (joins the
│                            # external lab-network for Consul, shared with my_elk_stack)
├── scripts/
│   ├── check-prereqs.ps1
│   └── check-prereqs.sh
├── device-status/
├── data-store/
├── device-control/
└── device-tests/
```

See [TODO.md](TODO.md) for the current task list and open design decisions.
