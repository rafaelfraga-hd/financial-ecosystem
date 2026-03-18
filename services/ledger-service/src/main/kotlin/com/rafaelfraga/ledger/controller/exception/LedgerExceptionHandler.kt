package com.rafaelfraga.ledger.controller.exception

import com.rafaelfraga.ledger.controller.response.LedgerEntryErrorResponse
import com.rafaelfraga.ledger.exception.LedgerConsistencyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class LedgerExceptionHandler {

    @ExceptionHandler(LedgerConsistencyException::class)
    fun handleLedgerConsistencyException(
        ex: LedgerConsistencyException
    ): ResponseEntity<LedgerEntryErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(
                LedgerEntryErrorResponse(
                    timestamp = Instant.now(),
                    status = 422,
                    error = LedgerExceptionMessages.LEDGER_CONSISTENCY_ERROR,
                    message = ex.message ?: LedgerExceptionMessages.DEFAULT_LEDGER_ERROR
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<LedgerEntryErrorResponse> {
        val message = ex.bindingResult.fieldErrors
            .firstOrNull()
            ?.defaultMessage
            ?: LedgerExceptionMessages.DEFAULT_VALIDATION_ERROR

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                LedgerEntryErrorResponse(
                    timestamp = Instant.now(),
                    status = 400,
                    error = LedgerExceptionMessages.VALIDATION_ERROR,
                    message = message
                )
            )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<LedgerEntryErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                LedgerEntryErrorResponse(
                    timestamp = Instant.now(),
                    status = 400,
                    error = LedgerExceptionMessages.VALIDATION_ERROR,
                    message = LedgerExceptionMessages.MALFORMED_OR_MISSING_REQUEST_BODY
                )
            )
    }

    companion object LedgerExceptionMessages {

        const val LEDGER_CONSISTENCY_ERROR = "Ledger consistency violation"
        const val DEFAULT_LEDGER_ERROR = "Invalid ledger entry"

        const val VALIDATION_ERROR = "Validation error"
        const val DEFAULT_VALIDATION_ERROR = "Request validation failed"
        const val MALFORMED_OR_MISSING_REQUEST_BODY = "Malformed JSON or missing required fields"
    }
}