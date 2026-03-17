# Transaction Service

The transaction-service is responsible for orchestrating all financial operations.

---

## Responsibilities

- create accounts
- handle deposits, withdrawals and transfers
- validate business rules
- enforce limits
- manage concurrency

---

## Supported Operations

### Deposit

- validate account status
- create ledger entry
- update daily balance

---

### Withdraw

- validate account status
- check balance + overdraft
- validate daily limits
- create ledger entry
- update daily balance

---

### Transfer

- validate source and destination accounts
- check limits and balance
- acquire locks (ordered)
- create ledger entries
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

## Concurrency

- per-account lock
- ordered locking for transfers

---

## Dependencies

- ledger-service
- repositories (accounts, balances, limits)

---

## Future Improvements

- idempotency support
- retry mechanisms
- better error handling
- double-entry modeling