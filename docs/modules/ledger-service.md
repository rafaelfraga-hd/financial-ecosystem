# Ledger Service

The ledger-service is responsible for recording and exposing financial events.

---

## Responsibilities

- store immutable ledger entries
- act as the source of truth
- support auditing
- enable balance reconstruction

---

## Ledger Model

Each financial operation is recorded as an entry:

- deposit
- withdraw
- transfer

Entries are **append-only** and never updated.

---

## Immutability

Ledger entries are never modified or deleted.

This guarantees:

- auditability
- traceability
- consistency

---

## Relationship with Balance

The ledger does not store balances.

Balances are derived from:

- ledger entries (source)
- daily_account_balances (snapshot)

---

## Query Capabilities

- list transactions by account
- filter by date range
- support audit queries

---

## Design Decisions

- append-only storage
- separation from transaction orchestration
- independent from balance representation

---

## Future Improvements

- double-entry accounting
- event streaming (Kafka / similar)
- projection-based balance calculation