CREATE TABLE ledger_entries
(
    id             BIGSERIAL PRIMARY KEY,
    transaction_id UUID           NOT NULL,
    account_id     BIGINT         NOT NULL,
    entry_type     VARCHAR(10)    NOT NULL,
    amount         NUMERIC(19, 2) NOT NULL,
    occurred_at    TIMESTAMPTZ    NOT NULL,
    recorded_at    TIMESTAMPTZ    NOT NULL,

    CONSTRAINT chk_ledger_entries_entry_type
        CHECK (entry_type IN ('CREDIT', 'DEBIT'))
);

CREATE INDEX idx_ledger_entries_transaction_id
    ON ledger_entries (transaction_id);

CREATE INDEX idx_ledger_entries_account_id
    ON ledger_entries (account_id);

CREATE INDEX idx_ledger_entries_account_id_occurred_at
    ON ledger_entries (account_id, occurred_at);