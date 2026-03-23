package com.rafaelfraga.ledger.domain

enum class LedgerEntryType {
    CREDIT,
    DEBIT;

    companion object {
        @JvmStatic
        fun from(value: String?): LedgerEntryType {
            return entries
                .firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid LedgerEntryType: $value")
        }
    }
}