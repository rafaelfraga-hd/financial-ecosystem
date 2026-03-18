package com.rafaelfraga.ledger.controller

import com.rafaelfraga.ledger.controller.mapper.toResponse
import com.rafaelfraga.ledger.controller.request.CreateLedgerEntryRequest
import com.rafaelfraga.ledger.controller.response.LedgerEntryResponse
import com.rafaelfraga.ledger.service.LedgerEntryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/ledger-entries")
class LedgerEntryController(val service: LedgerEntryService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<LedgerEntryResponse>> {
        val response = service.findAll().map { it.toResponse() }
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun save(@Valid @RequestBody request: CreateLedgerEntryRequest): ResponseEntity<LedgerEntryResponse> {
        val response = service.createEntry(
            request.transactionId,
            request.accountId,
            request.entryType,
            request.amount,
            request.occurredAt
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response.toResponse())
    }
}

