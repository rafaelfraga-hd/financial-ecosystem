# Financial Ecosystem

This project explores the design of a financial transaction system inspired by real-world payment platforms.

The goal is to implement and evolve core backend concepts commonly found in fintech systems, such as:

- ledger-based accounting
- transaction consistency
- concurrency control
- idempotency
- balance consolidation
- service decomposition

This repository is structured as an evolving system, progressing through multiple versions (V1, V2, V3...), each introducing new architectural and functional capabilities.

---

## Current Version: V1

The first version focuses on building a minimal but realistic financial transaction system with:

- persistence
- controlled concurrency
- ledger-based tracking
- daily balance consolidation
- basic financial constraints (limits and overdraft)

---

## Implemented

### transaction-service

Responsible for orchestrating financial operations:

- create accounts (with basic identity and configuration)
- deposit
- withdraw
- transfer between accounts
- validate business rules:
  - account status
  - daily limits
  - overdraft / negative balance

---

### ledger-service

Acts as the financial source of truth:

- stores immutable transaction records (ledger entries)
- supports auditability
- enables balance reconstruction
- exposes transaction history

---

### balance model

The system follows a **ledger + snapshot approach**:

- **ledger_entries** → source of truth (event-level)
- **daily_account_balances** → daily consolidated state

Each day stores:

- opening balance
- total credits
- total debits
- closing balance

This improves:

- read performance  
- historical tracking  
- auditability  

---

### persistence

- PostgreSQL as primary data store
- relational modeling with separation between:
  - accounts
  - ledger entries
  - daily balances
  - account limits
  - daily limit usage

---

### concurrency control

- per-account locking using `ReentrantLock`
- lock ordering strategy for safe transfers

Ensures consistency in a single-instance environment.

---

## Architecture (V1)

The system is structured as a **modular monolith**, composed of:

- **transaction-service**
  - handles operations and validations
- **ledger-service**
  - stores financial events and supports auditing

Even though services are logically separated, they run within the same runtime.

Detailed documentation is split into focused modules:

- `docs/architecture.md` — system overview  
- `docs/data-model.md` — database structure  
- `docs/modules/transaction-service.md`  
- `docs/modules/ledger-service.md`  

---

## Tech Stack

- Kotlin
- Spring Boot
- PostgreSQL
- Gradle
- (planned) Docker / Docker Compose

---

## Running the Project (planned)

The project will run via Docker Compose, including:

- application services
- PostgreSQL database

Setup instructions will be added as the implementation evolves.

---

## Status

🚧 Work in progress.

This project is intentionally built in iterations to explore real-world trade-offs in backend systems.

---

## Next Steps (V2)

- idempotency support (prevent duplicate transactions)
- improved transaction modeling (towards double-entry)
- database migrations (Flyway)
- better error handling and validation
- balance recalculation strategies
- initial observability (logging and metrics)

---

## Future Evolution

### V3 (planned)

- service decomposition (microservices)
- inter-service communication (REST / messaging)
- eventual consistency strategies
- distributed locking or transactional messaging

---

## Notes

This is a personal project focused on exploring backend engineering challenges found in financial systems.

The emphasis is not only on implementation, but on understanding and documenting:

- design decisions  
- trade-offs  
- system evolution over time  