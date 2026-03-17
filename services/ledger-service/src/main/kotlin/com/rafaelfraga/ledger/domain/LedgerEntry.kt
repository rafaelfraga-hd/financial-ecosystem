package com.rafaelfraga.ledger.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "ledger_entries")
class LedgerEntry(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "transaction_id", nullable = false)
    val transactionId: UUID,

    @Column(name = "account_id", nullable = false)
    val accountId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false)
    val entryType: LedgerEntryType,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "occurred_at", nullable = false)
    val occurredAt: Instant,

    @Column(name = "recorded_at", nullable = false)
    val recordedAt: Instant
)