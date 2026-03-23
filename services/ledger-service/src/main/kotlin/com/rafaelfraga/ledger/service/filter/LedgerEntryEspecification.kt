package com.rafaelfraga.ledger.service.filter

import com.rafaelfraga.ledger.domain.LedgerEntry
import com.rafaelfraga.ledger.domain.LedgerEntryType
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import jakarta.persistence.criteria.Predicate

object LedgerEntrySpecification {

    fun withFilter(filter: LedgerEntryFilter): Specification<LedgerEntry> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            filter.transactionId?.let {
                predicates.add(
                    criteriaBuilder.equal(root.get<UUID>("transactionId"), it)
                )
            }

            filter.accountId?.let {
                predicates.add(
                    criteriaBuilder.equal(root.get<Long>("accountId"), it)
                )
            }

            filter.entryType?.let {
                predicates.add(
                    criteriaBuilder.equal(root.get<LedgerEntryType>("entryType"), it)
                )
            }

            filter.amount?.let {
                predicates.add(
                    criteriaBuilder.equal(root.get<BigDecimal>("amount"), it)
                )
            }

            filter.occurredAt?.let {
                predicates.add(
                    criteriaBuilder.equal(root.get<Instant>("occurredAt"), it)
                )
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}