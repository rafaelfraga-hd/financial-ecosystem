package com.rafaelfraga.ledger.repository

import com.rafaelfraga.ledger.domain.LedgerEntry
import org.springframework.data.jpa.repository.JpaRepository

interface LedgerEntryRepository : JpaRepository<LedgerEntry, Long>