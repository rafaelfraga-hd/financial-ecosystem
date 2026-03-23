package com.rafaelfraga.ledger.controller

import com.rafaelfraga.ledger.controller.mapper.toResponse
import com.rafaelfraga.ledger.controller.request.CreateLedgerEntryRequest
import com.rafaelfraga.ledger.controller.request.LedgerEntryParameterRequest
import com.rafaelfraga.ledger.controller.response.LedgerEntryResponse
import com.rafaelfraga.ledger.service.LedgerEntryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/ledger-entries")
class LedgerEntryController(val service: LedgerEntryService) {

    @RestController
    @RequestMapping("/api/ledger-entries")
    class LedgerEntryController(
        private val service: LedgerEntryService
    ) {
        @GetMapping("/all")
        fun getAll(): ResponseEntity<List<LedgerEntryResponse>> {
            val response = service.findAll().map { it.toResponse() }
            return ResponseEntity.ok(response)
        }

        @GetMapping
        fun findEntries(
            @ModelAttribute request: LedgerEntryParameterRequest
        ): ResponseEntity<List<LedgerEntryResponse>> {
            val response = service.findEntries(request.toFilter())
                .map { it.toResponse() }

            return ResponseEntity.ok(response)
        }

        @PostMapping
        fun save(
            @Valid @RequestBody request: CreateLedgerEntryRequest
        ): ResponseEntity<LedgerEntryResponse> {
            val response = service.createEntry(
                request.transactionId,
                request.accountId,
                request.entryType,
                request.amount,
                request.occurredAt
            )

            return ResponseEntity.status(HttpStatus.CREATED).body(response.toResponse())
        }
    }
}

