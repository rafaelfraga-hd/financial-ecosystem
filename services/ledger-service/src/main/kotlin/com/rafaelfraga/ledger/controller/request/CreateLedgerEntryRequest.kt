package com.rafaelfraga.ledger.controller.request

import com.rafaelfraga.ledger.domain.LedgerEntryType
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class CreateLedgerEntryRequest(
    @field:NotNull
    val transactionId: UUID,
    @field:NotNull
    val accountId: Long,
    @field:NotNull
    val entryType: LedgerEntryType,
    @field:NotNull
    val amount: BigDecimal,
    @field:NotNull
    val occurredAt: Instant
)