package com.aequilibrium.transformers.exceptions.handler

import com.aequilibrium.transformers.response.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestExceptionHandler(
        private val mapper: ObjectMapper
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Exception::class])
    protected fun handleConflict(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        val responseStatus: ResponseStatus = ex.javaClass.getAnnotation(ResponseStatus::class.java)

        val exceptionResponse = ErrorResponse(ex.message!!, responseStatus.code.value())

        val bodyOfResponse = mapper.writeValueAsString(exceptionResponse)
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), responseStatus.code, request!!)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val fieldErrors = ex.bindingResult.fieldErrors
        return ResponseEntity(ErrorResponse(
                fieldErrors.map {
                    it.defaultMessage ?: ""
                }.joinToString(separator = ",") { it }, status.value()), status
        )
    }

}