# Architecture Overview

This document provides a high-level view of the system architecture for V1.

---

## Overview

The system is designed as a **modular monolith**, where services are logically separated but run within the same runtime.

This approach enables:

- fast iteration
- simplified deployment
- controlled complexity

Future versions will evolve towards distributed services.

---

## Core Components

### transaction-service

Handles business transaction orchestration:

- deposits
- withdrawals
- transfers
- account validation
- limit enforcement
- transaction creation

Acts as the entry point for all business operations.

---

### ledger-service

Responsible for financial record keeping:

- persists immutable ledger entries
- materializes business transactions into account postings
- enforces double-entry consistency (sum of entries = 0)
- acts as the source of truth for balances

Enables auditing and traceability.

---

## Architectural Style

- modular monolith
- layered design (controller → service → domain → repository)
- separation of concerns between transaction orchestration and data recording

---

## Data Flow

1. A request enters via `transaction-service`
2. Business rules are validated:
   - account status
   - balance / overdraft
   - daily limits
3. A business transaction is created
4. Transaction is materialized into ledger entries via `ledger-service`
5. Daily balance is updated
6. Locks are released

---

## Balance Strategy

The system follows a **ledger + snapshot model**:

- ledger entries = source of truth (derived from transactions)
- daily balances = derived state

This avoids relying on a mutable balance field.

---

## Concurrency Strategy

- per-account locking using `ReentrantLock`
- lock ordering for transfers

Ensures consistency in a single-instance system.

---

## Evolution Path

### V2
- idempotency
- improved transaction modeling
- better persistence strategies

### V3
- microservices
- async communication
- eventual consistency