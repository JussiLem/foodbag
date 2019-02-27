package me.foodbag.hello.web.controller

import lombok.extern.slf4j.Slf4j
import me.foodbag.hello.web.exception.InvalidOldPasswordException
import me.foodbag.hello.web.exception.UserAlreadyExistException
import me.foodbag.hello.web.exception.UserNotFoundException
import me.foodbag.hello.web.util.GenericResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Slf4j
@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @Autowired
    private val messages: MessageSource? = null

    // 400
    override fun handleBindException(
            ex: BindException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        log.error("400 Status Code", ex)
        val result = ex.bindingResult
        val bodyOfResponse = GenericResponse(result.allErrors, "Invalid" + result.objectName)
        return handleExceptionInternal(
                ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        log.error("400 Status Code", ex)
        val result = ex.bindingResult
        val bodyOfResponse = GenericResponse(result.allErrors, "Invalid" + result.objectName)
        return handleExceptionInternal(
                ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(InvalidOldPasswordException::class)
    fun handleInvalidOldPassword(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        log.error("400 Status Code", ex)
        val bodyOfResponse = GenericResponse(messages!!.getMessage("message.invalidOldPassword", null, request.locale), "InvalidOldPassword")
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    // 404
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        log.error("404 Status Code", ex)
        val bodyOfResponse = GenericResponse(messages!!.getMessage("message.userNotFound", null, request.locale), "UserNotFound")
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    // 409
    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleUserAlreadyExist(
            ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        log.error("409 Status Code", ex)
        val bodyOfResponse = GenericResponse(
                messages!!.getMessage("message.regError", null, request.locale), "UserAlreadyExist")
        return handleExceptionInternal(
                ex, bodyOfResponse, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

    @ExceptionHandler(Exception::class)
    fun handleInternal(
            ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        log.error("500 Status Code", ex)
        val bodyOfResponse = GenericResponse(
                messages!!.getMessage("message.error", null, request.locale), "InternalError")
        return ResponseEntity(
                bodyOfResponse, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
