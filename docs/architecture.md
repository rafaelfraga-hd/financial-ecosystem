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

Handles all financial operations:

- deposits
- withdrawals
- transfers
- account validation
- limit enforcement

Acts as the entry point for all business operations.

---

### ledger-service

Responsible for financial record keeping:

- stores immutable ledger entries
- acts as the source of truth
- enables auditing and traceability

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
3. Locks are acquired (if needed)
4. Ledger entries are created via `ledger-service`
5. Daily balance is updated
6. Locks are released

---

## Balance Strategy

The system follows a **ledger + snapshot model**:

- ledger entries = source of truth
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