package com.rafaelfraga.ledger.service.filter;

import com.rafaelfraga.ledger.domain.LedgerEntryType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record LedgerEntryFilter (
        UUID transactionId,
        Long accountId,
        LedgerEntryType entryType,
        BigDecimal amount,
        Instant occurredAt
){
}
