# Transaction Service

The transaction-service is responsible for orchestrating all business transactions.

---

## Responsibilities

- create and manage accounts
- orchestrate deposits, withdrawals and transfers
- create business transactions
- construct ledger entries for each transaction
- validate business rules
- enforce limits
- manage concurrency

---

## Supported Operations

### Deposit

- validate account status
- create business transaction
- construct ledger entry (credit)
- send entry to ledger-service for persistence
- update daily balance

---

### Withdraw

- validate account status
- check balance + overdraft
- validate daily limits
- create business transaction
- construct ledger entry (debit)
- send entry to ledger-service for persistence
- update daily balance

---

### Transfer

- validate source and destination accounts
- check limits and balance
- acquire locks (ordered)
- create business transaction
- construct ledger entries:
    - debit entry (source account)
    - credit entry (destination account)
- send entries to ledger-service for persistence
- update balances
- release locks

---

## Business Rules

### Account Status

Operations are only allowed for ACTIVE accounts.

---

### Overdraft

If enabled:

available_funds = balance + overdraft_limit

Transaction is rejected if amount exceeds available funds.

---

### Daily Limits

Before executing a transaction:

- retrieve today's usage
- validate against limits
- update usage

---

## Transaction Semantics

Each operation results in a **business transaction**, which is then translated into one or more ledger entries.

- deposit → 1 credit entry
- withdraw → 1 debit entry
- transfer → 2 entries (debit + credit)

---

## Concurrency

- per-account lock
- ordered locking for transfers

Ensures consistency in a single-instance system.

---

## Dependencies

- ledger-service
- repositories (accounts, balances, limits, transactions)

---

## Design Decisions

- separation between transaction orchestration and ledger recording
- transaction-service constructs ledger entries
- ledger-service persists and validates entries
- double-entry model for balanced transactions

---

## Future Improvements

- idempotency support
- retry mechanisms
- better error handling
- stronger consistency guarantees during transaction processing