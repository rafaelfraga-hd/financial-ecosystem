package com.rafaelfraga.ledger.controller.response

import com.rafaelfraga.ledger.domain.LedgerEntry
import com.rafaelfraga.ledger.domain.LedgerEntryType
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class LedgerEntryResponse(
    val id: Long,
    val transactionId: UUID,
    val accountId: Long,
    val entryType: LedgerEntryType,
    val amount: BigDecimal,
    val occurredAt: Instant,
    val recordedAt: Instant
)