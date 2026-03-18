package com.rafaelfraga.ledger.controller.exception

import com.rafaelfraga.ledger.controller.response.LedgerEntryErrorResponse
import com.rafaelfraga.ledger.exception.LedgerConsistencyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class LedgerExceptionHandler {

    @ExceptionHandler(LedgerConsistencyException::class)
    fun handleLedgerConsistencyException(ex: LedgerConsistencyException) : ResponseEntity<LedgerEntryErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
            LedgerEntryErrorResponse(Instant.now(),
                422,
                LEDGER_CONSISTENCY_ERROR,
                message = ex.message ?: DEFAULT_LEDGER_ERROR)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<LedgerEntryErrorResponse> {
        val message = ex.bindingResult.fieldErrors
            .firstOrNull()
            ?.defaultMessage
            ?: DEFAULT_VALIDATION_ERROR

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                LedgerEntryErrorResponse(
                    timestamp = Instant.now(),
                    status = 400,
                    error = VALIDATION_ERROR,
                    message = message
                )
            )
    }

    companion object LedgerExceptionMessages {

        const val LEDGER_CONSISTENCY_ERROR = "Ledger consistency violation"
        const val DEFAULT_LEDGER_ERROR = "Invalid ledger entry"

        const val VALIDATION_ERROR = "Validation error"
        const val DEFAULT_VALIDATION_ERROR = "Request validation failed"
    }
}