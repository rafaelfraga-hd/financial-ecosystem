package com.rafaelfraga.ledger.controller

import com.rafaelfraga.ledger.repository.LedgerEntryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.get
import tools.jackson.databind.ObjectMapper
import java.util.UUID

@SpringBootTest
@AutoConfigureMockMvc
class LedgerEntryControllerIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val repository: LedgerEntryRepository
) {

    @BeforeEach
    fun setup() {
        repository.deleteAll()
    }

    @Test
    fun `should create ledger entry when request is valid`() {
        val request = mapOf(
            "transactionId" to UUID.randomUUID().toString(),
            "accountId" to 1,
            "entryType" to "CREDIT",
            "amount" to 100.50,
            "occurredAt" to "2026-03-17T20:00:00Z"
        )

        mockMvc.post("/api/ledger-entries") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isCreated() } // troque para isCreated() se mudar o controller para 201
                jsonPath("$.id") { exists() }
                jsonPath("$.transactionId") { value(request["transactionId"]) }
                jsonPath("$.accountId") { value(1) }
                jsonPath("$.entryType") { value("CREDIT") }
                jsonPath("$.amount") { value(100.50) }
                jsonPath("$.occurredAt") { value("2026-03-17T20:00:00Z") }
                jsonPath("$.recordedAt") { exists() }
            }
    }

    @Test
    fun `should return all ledger entries`() {
        val firstRequest = mapOf(
            "transactionId" to UUID.randomUUID().toString(),
            "accountId" to 1,
            "entryType" to "CREDIT",
            "amount" to 100.50,
            "occurredAt" to "2026-03-17T20:00:00Z"
        )

        val secondRequest = mapOf(
            "transactionId" to UUID.randomUUID().toString(),
            "accountId" to 1,
            "entryType" to "DEBIT",
            "amount" to -50.00,
            "occurredAt" to "2026-03-17T20:05:00Z"
        )

        mockMvc.post("/api/ledger-entries") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(firstRequest)
        }

        mockMvc.post("/api/ledger-entries") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(secondRequest)
        }

        mockMvc.get("/api/ledger-entries")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(2) }
                jsonPath("$[0].id") { exists() }
                jsonPath("$[1].id") { exists() }
            }
    }

    @Test
    fun `should return 422 when credit amount is negative`() {
        val request = mapOf(
            "transactionId" to UUID.randomUUID().toString(),
            "accountId" to 1,
            "entryType" to "CREDIT",
            "amount" to -10.00,
            "occurredAt" to "2026-03-17T20:10:00Z"
        )

        mockMvc.post("/api/ledger-entries") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isUnprocessableEntity() }
                jsonPath("$.status") { value(422) }
                jsonPath("$.error") { value("Ledger consistency violation") }
                jsonPath("$.message") { value("Credit entries must have positive amount") }
            }
    }

    @Test
    fun `should return 400 when request body is invalid`() {
        val invalidRequest = mapOf(
            "accountId" to 1,
            "entryType" to "CREDIT",
            "amount" to 100.00
        )

        mockMvc.post("/api/ledger-entries") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidRequest)
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.status") { value(400) }
                jsonPath("$.error") { value("Validation error") }
            }
    }
}