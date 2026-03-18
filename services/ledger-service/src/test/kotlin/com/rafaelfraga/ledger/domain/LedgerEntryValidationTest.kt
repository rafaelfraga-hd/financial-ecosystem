package com.rafaelfraga.ledger.domain

import com.rafaelfraga.ledger.domain.validations.validateConsistency
import com.rafaelfraga.ledger.exception.LedgerConsistencyException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.util.UUID
import java.time.Instant


class LedgerEntryValidationTest {

    @Test
    fun `should throw exception when credit is negative`(){
        val entry = ledgerEntry(
            entryType = LedgerEntryType.CREDIT,
            amount = BigDecimal.valueOf(-10)
        )

        assertThrows(LedgerConsistencyException::class.java) {
            entry.validateConsistency()
        }
    }

    @Test
    fun `shoud pass when credit is positive and debit is negative`() {
        val entry1 = ledgerEntry(
            entryType = LedgerEntryType.CREDIT,
            amount = BigDecimal.valueOf(10)
        )

        val entry2 = ledgerEntry(
            entryType = LedgerEntryType.DEBIT,
            amount = BigDecimal.valueOf(-10)
        )

        val list = listOf<LedgerEntry>(entry1, entry2)

        assertDoesNotThrow(){
            list[0].validateConsistency()
            list[1].validateConsistency()
        }
    }

    @Test
    fun `should throw exception when debit is positive`(){
        val entry = ledgerEntry(
            entryType = LedgerEntryType.DEBIT,
            amount = BigDecimal.valueOf(10)
        )

        assertThrows(LedgerConsistencyException::class.java) {
            entry.validateConsistency()
        }
    }

    private fun ledgerEntry(
        entryType: LedgerEntryType,
        amount: BigDecimal
    ): LedgerEntry {
        return LedgerEntry(
            id = null,
            transactionId = UUID.randomUUID(),
            accountId = 1,
            entryType = entryType,
            amount = amount,
            occurredAt = Instant.now(),
            recordedAt = Instant.now()
        )
    }
}