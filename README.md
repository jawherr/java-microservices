# Java Microservices

This repository is a blueprint and starter kit for building high-performance, production-ready Java backend systems that can be implemented either as a performant monolith or as a scalable set of microservices.

## Goals
- Provide a pragmatic, senior-backend-developer-approved architecture and tooling selection.
- Show recommended technology choices (Spring Boot + Quarkus) and cross-cutting tools for DB, messaging, caching, CI/CD, observability and security.
- Offer an incremental path: start with a modular high-performance monolith and split to microservices where it makes sense.

## High-level vision
- Keep core business logic domain-driven and framework-agnostic.
- Design for observability, security, and automation from day one.
- Use modern, proven tools to enable fast developer feedback and safe production deployments.

## Key non-functional requirements
- Performance: low latency (sub-100ms P95 for typical API calls), high throughput.
- Scalability: horizontal scalability for stateless services, and appropriate patterns for stateful services.
- Reliability: graceful degradation, retries, bulkheads, and circuit breakers.
- Maintainability: small modules/contexts, strong typing, and automated tests.
- Observability: structured logs, distributed tracing, metrics, and alerting.

## Recommended tech stack
- JVM & language: Java 17 or 21 (LTS), use modern language features where helpful.
- Frameworks:
  - Spring Boot 3.x for full-featured, ecosystem-rich services (REST, Spring Data, Spring Security).
  - Quarkus for performance-sensitive services (fast startup, low memory) and native compilation where needed.
- Build tools: Maven or Gradle (pick one consistently). Provide sample Maven setup by default.
- Database: PostgreSQL (primary OLTP store). Use R2DBC for reactive services where appropriate; otherwise use JDBC via Spring Data JPA.
- Migrations: Flyway (or Liquibase) for database schema/version control.
- Caching: Redis (for distributed caches) or Hazelcast (in-memory grid) when needed.
- Messaging / events: Apache Kafka (event-driven, durable streaming) and RabbitMQ (if you need simpler broker semantics).
- API protocols: REST (OpenAPI), gRPC for high-performance polyglot RPC, GraphQL optional for rich client queries.
- Security: OAuth 2.0 / OIDC (Keycloak or Auth0), JWT tokens, Spring Security, and Vault for secrets.
- Observability: OpenTelemetry (traces + metrics), Jaeger/Tempo for tracing, Prometheus for metrics, Grafana for dashboards.
- Testing: JUnit 5, Mockito, Testcontainers (for integration tests using real Postgres/Kafka), Pact for contract testing.
- Performance testing: Gatling or k6 for load tests; JMH for microbenchmarks.
- Containerization / orchestration: Docker, Kubernetes (K8s), Helm charts; optionally use Kustomize or ArgoCD for GitOps.
- CI/CD: GitHub Actions or GitLab CI for pipelines, with stages for build, test, security scans, container publish, and deploy.
- Secrets & Config: HashiCorp Vault for secrets, Spring Cloud Config / Consul or environment-driven 12-factor config for app configuration.

## Architecture patterns & design principles
- Start as a modular monolith (multi-module Gradle/Maven project) organized by bounded contexts. This gives fast developer feedback and avoids premature distributed systems complexity.
- Apply Hexagonal / Ports & Adapters architecture to keep business logic independent from frameworks and infrastructure.
- Use Domain-Driven Design (DDD) to identify bounded contexts and where to split into microservices.
- Prefer asynchronous messaging and event-driven integration for inter-service communication when eventual consistency is acceptable.
- Use API Gateway for external APIs (rate-limiting, authentication, routing). Keep internal APIs lightweight.
- Use health checks (liveness/readiness), graceful shutdown, and resource limits for containers.

## Project layout suggestions
- Monolith (modular):
  - /app (service application starters) - Spring Boot / Quarkus launchers
  - /domain (shared domain model & services)
  - /infrastructure (db, messaging, cache adapters)
  - /api (REST controllers / gRPC endpoints)
  - /integration-tests (Testcontainers-based tests)
- Microservices:
  - service-name/ (each service: own module/repo, own Dockerfile, Helm chart)
  - shared-libs/ (common libraries maintained with versioning)

## Implementation contract (short)
- Inputs: HTTP/gRPC requests, async messages, DB events.
- Outputs: HTTP/gRPC responses, domain events, DB writes, metrics, logs, traces.
- Error modes: transient infra failures (handled by retries & backoff), validation errors (client 4xx), auth/permission (401/403).
- Success criteria: automated build + tests, deployable container image, passing health checks, and basic load test within target SLOs.

## Developer UX & local dev setup
- Provide a docker-compose file to bring up Postgres, Kafka (or RabbitMQ), Redis, and Keycloak for local development.
- Use dev profiles (Spring profiles or Quarkus config) to switch between in-memory/mocked dependencies and real infra.
- Prefer devtools / hot-reload (Spring DevTools, Quarkus dev mode) for fast feedback.

## CI/CD pipeline outline
1. Checkout code and run static analysis (spotbugs, checkstyle, dependency-check).
2. Build with Maven/Gradle and run unit tests.
3. Run integration tests using Testcontainers (or a test environment).
4. Build Docker image and run a lightweight smoke test.
5. Push image to registry and create an immutable version/tag.
6. Deploy to staging via Helm or GitOps, run end-to-end and contract tests.
7. Promote to production with a controlled rollout (canary, blue/green).

## Security checklist
- Enforce HTTPS everywhere (nginx/ALB + service TLS).
- Use OAuth2/OIDC for authentication; never roll your own auth.
- Validate and sanitize inputs; use parameterized queries or ORM to prevent SQL injection.
- Short-lived JWTs + refresh tokens; rotate secrets stored in Vault.
- Apply principle of least privilege for service accounts and database credentials.
- Use static analysis and dependency vulnerability scanning (Snyk, Dependabot, or OWASP Dependency-Check) in the pipeline.

## Observability & SLOs
- Capture structured logs (JSON) with request IDs.
- Export traces and metrics via OpenTelemetry libraries to Jaeger and Prometheus.
- Define SLOs (error rates, latency P95/P99) and set up Grafana dashboards and alerts.

## Testing strategy
- Unit tests for business logic (JUnit 5 + Mockito), aim for fast execution.
- Integration tests using Testcontainers to run Postgres/Kafka in CI for realistic integration.
- Contract tests (Consumer-Driven Contracts) to protect service boundaries.
- End-to-end smoke tests after deployment to staging.
- Load testing in staging with real-ish data using Gatling/k6.

## Performance tips
- Prefer connection pooling (HikariCP), efficient query patterns, and proper indexing for Postgres.
- For high concurrency paths, consider reactive stacks (R2DBC, WebFlux, Mutiny in Quarkus) and benchmark carefully.
- Use caching for read-heavy endpoints; measure cache hit ratio and TTLs.
- Profile hot paths using async-profiler / JFR and iterate.

## Minimal MVP (first milestone)
1. Core domain module with a single bounded context (e.g., Orders, Users, Inventory) implemented in a modular monolith.
2. REST API with OpenAPI docs and basic CRUD flows.
3. Postgres persistence with Flyway-managed schema.
4. Dockerfile and docker-compose for local dev (Postgres + Redis + Keycloak minimal).
5. CI pipeline with build, unit tests and a basic integration stage.
6. Logging, health endpoints, and a Prometheus metrics endpoint.

## Roadmap & next steps
- Phase 1: Create modular monolith with domain-first design + baseline CI and infra (DB, cache).
- Phase 2: Add observability (traces, metrics, dashboards) and security (Keycloak integration).
- Phase 3: Introduce asynchronous messaging and eventing for selected flows.
- Phase 4: Split off the first microservice from monolith (bounded context extraction) and deploy to Kubernetes.
- Phase 5: Harden CI/CD, rollout strategies, and secrets management.

