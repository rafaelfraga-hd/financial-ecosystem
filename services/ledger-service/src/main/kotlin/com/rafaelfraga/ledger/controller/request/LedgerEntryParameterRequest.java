package com.rafaelfraga.ledger.controller.request;

import com.rafaelfraga.ledger.domain.LedgerEntryType;
import com.rafaelfraga.ledger.service.filter.LedgerEntryFilter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record LedgerEntryParameterRequest (
        UUID transactionId,
        Long accountId,
        String entryType,
        BigDecimal amount,
        String occurredAt
){
    public LedgerEntryFilter toFilter() {
        return new LedgerEntryFilter(
                transactionId,
                accountId,
                eventTypeParse(entryType),
                amount,
                instantParse(occurredAt)
        );
    }

    private Instant instantParse(String value) {
        return value == null || value.isBlank() ? null : Instant.parse(value);
    }

    private LedgerEntryType eventTypeParse(String value) {
        return value == null || value.isBlank() ? null : LedgerEntryType.from(value);
    }
}
