# TODO

## Scaffolding (done)
- [x] Parent aggregator POM (Java 21, Spring Boot 3.3.4, Spring Cloud 2023.0.3)
- [x] Prerequisite check scripts (`scripts/check-prereqs.ps1`, `scripts/check-prereqs.sh`)
- [x] `device-status` module skeleton (status get/put endpoints, Consul registration, Log4j2)
- [x] `data-store` module skeleton (register/list/get device records, Consul registration, Log4j2)
- [x] `device-control` module skeleton (power on/off, LED control, ROS2-style cmd_vel/stop)
- [x] `device-tests` integration test module skeleton, kept as a separate project from the
      three services, not run by default (`skipITs=true`, enable with `-DskipITs=false`),
      and not part of `docker compose up`
- [x] Unit tests for each service's controllers (`device-status`, `data-store`, `device-control`);
      run automatically as part of `mvn clean install`
- [x] Dockerfiles for all three services + docker-compose wiring (Consul + device-status +
      data-store + device-control all start together with `docker compose up -d --build`)
- [x] README


## Next up
- [ ] Stand up local ELK stack (docker-compose: Elasticsearch, Logstash, Kibana) and wire
      Log4j2 socket appenders in each service to the Logstash TCP input.
- [ ] Replace in-memory maps in `device-status` and `data-store` with a real datastore
      (start with H2/Postgres) once the basic flow is validated.
- [ ] Have `device-control` call `data-store` to validate device IDs before sending commands.
- [ ] Add global `@ControllerAdvice` exception handling (consistent error payloads) to each service.
- [ ] Add request/response logging (e.g. via a `Filter` or interceptor) for traceability.
- [ ] Add correlation/trace IDs across services (Spring Cloud Sleuth / Micrometer Tracing).
- [ ] Enable and flesh out `device-tests` ITs once services have real persistence; consider
      Testcontainers to spin up services automatically instead of requiring them pre-started.
- [ ] Add CI pipeline (build + unit tests) and a separate job for integration tests.
- [ ] Add OpenAPI/Swagger docs to each service.
- [ ] Decide on auth strategy between services (mTLS, API keys, or none for sandbox).
- [ ] Containerize each service (Dockerfile) for local docker-compose runs alongside Consul/ELK.

## Open questions
- Real transport for device commands (REST to devices directly vs. MQTT/message broker)?
- Should `device-control` proxy through `data-store` for every command, or cache device metadata locally?
- Persistence choice for `data-store` (relational vs. document store) long-term.
