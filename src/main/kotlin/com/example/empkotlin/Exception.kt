package com.example.empkotlin

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    fun handleNotFoundException(ex: EntityNotFoundException): ResponseStatusException {
        return ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")
    }
}