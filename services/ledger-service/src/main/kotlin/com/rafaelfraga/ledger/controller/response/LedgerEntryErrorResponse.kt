package com.rafaelfraga.ledger.controller.response

import java.time.Instant

data class LedgerEntryErrorResponse(
    val timestamp: Instant,
    val status: Int,
    val error: String,
    val message: String
)