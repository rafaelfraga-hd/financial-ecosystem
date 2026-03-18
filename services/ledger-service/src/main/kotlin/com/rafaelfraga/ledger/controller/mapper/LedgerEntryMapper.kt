package com.rafaelfraga.ledger.controller.mapper

import com.rafaelfraga.ledger.controller.response.LedgerEntryResponse
import com.rafaelfraga.ledger.domain.LedgerEntry

fun LedgerEntry.toResponse(): LedgerEntryResponse {
    return LedgerEntryResponse(
        id = this.id!!,
        transactionId = this.transactionId,
        accountId = this.accountId,
        entryType = this.entryType,
        amount = this.amount,
        occurredAt = this.occurredAt,
        recordedAt = this.recordedAt
    )
}