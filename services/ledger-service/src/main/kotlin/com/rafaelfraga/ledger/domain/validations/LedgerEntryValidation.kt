package com.rafaelfraga.ledger.domain.validations

import com.rafaelfraga.ledger.domain.LedgerEntry
import com.rafaelfraga.ledger.domain.LedgerEntryType
import com.rafaelfraga.ledger.exception.LedgerConsistencyException
import com.rafaelfraga.ledger.exception.LedgerErrorMessages

fun LedgerEntry.validateConsistency() {
    when (entryType) {
        LedgerEntryType.CREDIT -> {
            if (amount.signum() <= 0) {
                throw LedgerConsistencyException(LedgerErrorMessages.INVALID_CREDIT_AMOUNT)
            }
        }

        LedgerEntryType.DEBIT -> {
            if (amount.signum() >= 0) {
                throw LedgerConsistencyException(LedgerErrorMessages.INVALID_DEBIT_AMOUNT)
            }
        }
    }
}