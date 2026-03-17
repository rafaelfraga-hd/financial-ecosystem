# Data Model (V1)

The system uses a **ledger-based model**, separating transactional events from balance representation.

---

## accounts

Stores account identity and configuration.

- id
- account_number
- branch_number
- bank_code
- holder_name
- account_type
- currency
- status
- allow_negative_balance
- overdraft_limit
- created_at
- updated_at

---

## ledger_entries

Represents immutable financial events.

- id
- transaction_id
- entry_type (deposit, withdraw, transfer)
- source_account_id
- destination_account_id
- amount
- created_at

Each operation generates one or more ledger entries.

---

## daily_account_balances

Represents the daily consolidated position of each account.

- id
- account_id
- balance_date
- opening_balance
- total_credits
- total_debits
- closing_balance
- created_at
- updated_at

### Rules

- closing_balance = opening_balance + total_credits - total_debits
- next day opening_balance = previous closing_balance

---

## account_limits

Stores per-account limits.

- id
- account_id
- daily_withdraw_limit
- daily_transfer_limit
- daily_debit_limit
- created_at
- updated_at

---

## daily_limit_usage

Tracks usage of limits per day.

- id
- account_id
- usage_date
- withdraw_used
- transfer_used
- debit_used
- created_at
- updated_at

---

## Design Decisions

- ledger entries are immutable
- balance is derived, not stored as a single field
- daily snapshots improve read performance
- limits are separated from account configuration