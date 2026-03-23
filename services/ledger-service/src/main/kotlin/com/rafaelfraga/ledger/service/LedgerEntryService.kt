package com.rafaelfraga.ledger.service

import com.rafaelfraga.ledger.domain.LedgerEntry
import com.rafaelfraga.ledger.domain.LedgerEntryType
import com.rafaelfraga.ledger.domain.validations.validateConsistency
import com.rafaelfraga.ledger.repository.LedgerEntryRepository
import com.rafaelfraga.ledger.service.filter.LedgerEntryFilter
import com.rafaelfraga.ledger.service.filter.LedgerEntrySpecification
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant
import java.util.Enumeration
import java.util.UUID

@Service
class LedgerEntryService(
    private val repository: LedgerEntryRepository
) {

    fun createEntry(
        transactionId: UUID,
        accountId: Long,
        entryType: LedgerEntryType,
        amount: BigDecimal,
        occurredAt: Instant
    ): LedgerEntry {

        val entry = LedgerEntry(
            transactionId = transactionId,
            accountId = accountId,
            entryType = entryType,
            amount = amount,
            occurredAt = occurredAt,
            recordedAt = Instant.now()
        )

        entry.validateConsistency()
        return repository.save(entry)
    }

    fun findAll(): List<LedgerEntry> {
        return repository.findAll()
    }

    fun findEntries(filter: LedgerEntryFilter): List<LedgerEntry> {
        return repository.findAll() //LedgerEntrySpecification.withFilter(filter))
    }
}
